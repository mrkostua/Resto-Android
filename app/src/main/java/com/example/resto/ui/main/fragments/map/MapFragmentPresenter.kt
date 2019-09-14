package com.example.resto.ui.main.fragments.map


import android.location.Location
import com.example.resto.data.repositories.RestaurantsRepository
import com.example.resto.ui.BaseFragmentPresenter
import com.example.resto.util.managers.LocationManager
import com.example.resto.util.managers.LocationManagerImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MapFragmentPresenter @Inject constructor(
    private val mLocationManager: LocationManager,
    private val mRestaurantsRepository: RestaurantsRepository
) :
    BaseFragmentPresenter<MapFragmentContract.View>(),
    MapFragmentContract.Presenter, LocationManagerImpl.LocationManagerInterface {

    private var mLastUserLocation: Location? = null

    override fun attachView(view: MapFragmentContract.View) {
        super.attachView(view)
        mLocationManager.setLocationInterface(this)
        checkLocationServiceConnection()
        checkLocationSettings()
        observeUserLocation()
    }

    //region presenter methods
    override fun onLocationButtonClicked() {
        mLastUserLocation?.let {
            view?.centerOnUser(it.latitude, it.longitude)
        }
    }


    override fun onMapReady() {
        view?.showProgress()
        loadRestaurantsMarkersOnMap()
    }

    override fun onResumed() {
        centerOnUserIfPossible()
    }

    override fun onPaused() {
        mLocationManager.stopLocationUpdates()
    }
    //endregion

    //region location manager interface methods
    override fun onLocationPermissionGranted(isGranted: Boolean) {
        Timber.d("onLocationPermissionGranted")
    }

    override fun onConnected(isConnected: Boolean) {
        Timber.d("onConnected")
        if (isConnected) {
            checkLocationSettings()
        }
    }

    override fun onLocationSettingsOn(isOn: Boolean) {
        Timber.d("onLocationSettingsOn")
        if (isOn) {
            mLocationManager.startLocationUpdates()
        }
    }
    // endregion

    //region private methods
    private fun checkLocationSettings() {
        mLocationManager.checkLocationSettings()
    }

    private fun centerOnUserIfPossible() {
        mLocationManager.checkLocationSettings()
        mLastUserLocation?.let {
            view?.centerOnUser(it.latitude, it.longitude)
        } ?: run {
            mLocationManager.startLocationUpdates()
        }
    }

    private fun observeUserLocation() {
        val disposable = mLocationManager
            .lastLocation
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mLastUserLocation = it
                view?.centerOnUser(it.latitude, it.longitude)
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }

    private fun checkLocationServiceConnection() {
        if (mLocationManager.isConnected) {
            onConnected(true)
        } else {
            mLocationManager.connect()
        }
    }

    private fun loadRestaurantsMarkersOnMap() {
        compositeDisposable.add(
            mRestaurantsRepository.getAllRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restaurants ->
                    view?.drawRestaurantsMarkersOnMap(restaurants)
                    view?.hideProgress()
                }, { error ->
                    view?.hideProgress()
                    view?.showPopupNoRestaurants()
                    Timber.e(error)
                })
        )
    }

    //endregion
}
