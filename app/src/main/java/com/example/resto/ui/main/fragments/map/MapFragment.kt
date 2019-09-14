package com.example.resto.ui.main.fragments.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import com.example.resto.R
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.resto.ui.BaseFragment
import com.example.resto.util.config.REQUEST_CHECK_PERMISSIONS
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*

import javax.inject.Inject

class MapFragment : BaseFragment(), MapFragmentContract.View,
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    companion object {
        @JvmStatic
        val TAG = "com.example.resto.ui.main.fragments.map.MapFragment"

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    private val initialZoomLevel = 16.0f

    @Inject
    internal lateinit var mPresenter: MapFragmentContract.Presenter

    override val layoutId = R.layout.fragment_map

    private lateinit var mMap: GoogleMap


    private var locationMarker: Marker? = null
    private var locationMarkerOptions: MarkerOptions? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapMv.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapMv.onResume()
        mPresenter.onResumed()
    }

    override fun onPause() {
        super.onPause()
        mapMv.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapMv.onLowMemory()
    }

    //region init
    override fun init() {
        super.init()
        initMap()
        mPresenter.attachView(this)
        initListeners()
    }
    //endregion

    //region google map methods
    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        mPresenter.onMapReady()

        mMap.setOnMarkerClickListener(this)
/*        val customInfoWindow = context?.let { CustomInfoWindow(it) }
        mMap.setInfoWindowAdapter(customInfoWindow)*/

        val polandLatLng = LatLng(50.076028, 19.929025)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(polandLatLng, 15F))

    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        checkPermissions()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker?.position, initialZoomLevel))
        return true
    }
    //endregion

    //region view
    override fun centerOnUser(lat: Double, lon: Double) {
        if (locationMarker == null) {
            locationMarkerOptions = MarkerOptions().position(LatLng(lat, lon)).icon(getPinAsBitmap())
            locationMarker = mMap.addMarker(locationMarkerOptions)
        } else {
            locationMarker!!.position = LatLng(lat, lon)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), initialZoomLevel))
    }
    //endregion

    //region private methods
    private fun initMap() {
        mapMv.getMapAsync(this)
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_CHECK_PERMISSIONS
            )
            return
        }
    }

    private fun initListeners() {
        xLocationButton.setOnClickListener {
            mPresenter.onLocationButtonClicked()
        }
    }

    private fun getPinAsBitmap(): BitmapDescriptor {
        val myLocationPin = ContextCompat.getDrawable(context!!, R.drawable.location_marker)
        myLocationPin!!.setBounds(0, 0, myLocationPin.intrinsicWidth - 15, myLocationPin.intrinsicHeight - 15)
        val pinBitmap =
            Bitmap.createBitmap(myLocationPin.intrinsicWidth, myLocationPin.intrinsicWidth, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(pinBitmap)
        myLocationPin.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(pinBitmap)
    }

    //endregion
}