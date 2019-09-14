package com.example.resto.util.managers

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.net.Uri
import android.os.Bundle
import com.example.resto.ui.main.MainActivity
import com.example.resto.util.config.REQUEST_CHECK_PERMISSIONS
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LocationManagerImpl @Inject constructor(
    private val context: Context
) : GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener,
    LocationManager {

    //region LocationManager values
    private val _locationUnavailable: PublishSubject<Any> = PublishSubject.create()
    override val locationUnavailable: Observable<Any>
        get() = _locationUnavailable

    private val _lastLocation: PublishSubject<Location> = PublishSubject.create()
    override val lastLocation: Observable<Location>
        get() = _lastLocation

    override val isConnected: Boolean
        get() = mGoogleApiClient!!.isConnected
    //endregion

    //region private fields
    private val mGeocoder: Geocoder = Geocoder(context, Locale.getDefault())
    private val mLocationCallback = LocationCallback()

    private val mLocationRequest: LocationRequest
    private val mGoogleApiClient: GoogleApiClient?
    private var mManagerInterface: LocationManagerInterface? = null
    private var checkLocationSettingsTask: Task<LocationSettingsResponse>? = null
    //endregion

    init {
        mGoogleApiClient = createGoogleApiClient(context)
        mLocationRequest = createLocationRequest()
    }

    //region LocationManager methods
    override fun setLocationInterface(locationInterface: LocationManagerInterface): LocationManagerImpl {
        mManagerInterface = locationInterface
        return this
    }

    override fun connect() {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected)
            mGoogleApiClient.connect()
    }


    override fun checkLocationSettings() {
        if (checkLocationSettingsTask == null) {
            val builder = LocationSettingsRequest.Builder()
                .setAlwaysShow(true)
                .addLocationRequest(mLocationRequest)
            val settingsClient = LocationServices.getSettingsClient(context)
            checkLocationSettingsTask = settingsClient.checkLocationSettings(builder.build())
            checkLocationSettingsTask!!.addOnSuccessListener {
                mManagerInterface!!.onLocationSettingsOn(true)
            }
            checkLocationSettingsTask!!.addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        it.resolution.send(
                            context,
                            REQUEST_CHECK_PERMISSIONS,
                            Intent(context, MainActivity::class.java)
                        )
                    } catch (sendException: IntentSender.SendIntentException) {
                        //just ignore
                    }
                }
            }
        }
    }

    override fun startLocationUpdates() {
        Timber.d("startLocationUpdates")
        try {
            Timber.d("startLocationUpdates success")
            LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
            LocationServices.getFusedLocationProviderClient(context).lastLocation?.let {
                it.addOnSuccessListener { location ->
                    _lastLocation.onNext(location)
                }
            }
        } catch (e: SecurityException) {
            Timber.d("startLocationUpdates SecurityException")
            if (mManagerInterface != null) {
                mManagerInterface!!.onLocationPermissionGranted(false)
            }
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            Timber.d("startLocationUpdates IllegalStateException")
            if (mManagerInterface != null) {
                mManagerInterface!!.onLocationPermissionGranted(true)
            }
            e.printStackTrace()
        }
    }

    override fun stopLocationUpdates() {
        if (mGoogleApiClient!!.isConnected) {
            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(mLocationCallback)
        }
    }
    //endregion

    //region LocationListener
    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }
    //endregion

    //region GoogleApiClient.ConnectionCallbacks
    override fun onConnected(bundle: Bundle?) {
        mManagerInterface!!.onConnected(true)
    }

    override fun onConnectionSuspended(i: Int) {
        mManagerInterface!!.onConnected(false)
    }
    //endregion

    //region GoogleApiClient.OnConnectionFailedListener
    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        if (connectionResult.errorCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
            val context = mGoogleApiClient!!.context
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                intent.data = Uri.parse("market://details?id=com.google.android.gms")
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                intent.data = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms")
                context.startActivity(intent)
            }
        } else if (connectionResult.errorCode == ConnectionResult.SERVICE_DISABLED)
            _locationUnavailable.onNext(true)

        mManagerInterface!!.onConnected(false)
    }
    //endregion

    override fun onLocationChanged(location: Location) {
        Timber.d("onLocationChanged ${location.latitude} ${location.longitude}")
        _lastLocation.onNext(location)
    }

    //region private methods
    private fun createLocationRequest(): LocationRequest {
        return LocationRequest()
            .setInterval(5000)
            .setFastestInterval(3000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun createGoogleApiClient(context: Context): GoogleApiClient {
        return GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
    }
    //endregion

    //region interfaces
    interface LocationManagerInterface {
        fun onLocationPermissionGranted(isGranted: Boolean)
        fun onConnected(isConnected: Boolean)
        fun onLocationSettingsOn(isOn: Boolean)
    }
    //endregion
}