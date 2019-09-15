package com.example.resto.ui.main.fragments.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import com.example.resto.R
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.resto.data.RestaurantModel
import com.example.resto.ui.BaseFragment
import com.example.resto.util.config.REQUEST_CHECK_PERMISSIONS
import com.example.resto.util.views.SimplePopupDialog
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.jetbrains.anko.support.v4.toast
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

        val polandLatLng = LatLng(50.076028, 19.929025)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(polandLatLng, 15F))

    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        checkPermissions()
        marker?.tag?.let { id ->
            if (id is Int) {
                mPresenter.onRestaurantMarkerClicked(id)
            }
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker?.position, initialZoomLevel))
        return true
    }
    //endregion

    //region view
    override fun centerOnUser(lat: Double, lon: Double) {
        if (locationMarker == null) {
            locationMarkerOptions = MarkerOptions().position(LatLng(lat, lon))
            context?.let { notEmptyContext ->
                locationMarkerOptions?.icon(
                    bitmapDescriptorFromVector(
                        notEmptyContext,
                        R.drawable.location_marker
                    )
                )
            }
            locationMarker = mMap.addMarker(locationMarkerOptions)
        } else {
            locationMarker?.position = LatLng(lat, lon)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), initialZoomLevel))
    }

    override fun drawRestaurantsMarkersOnMap(restaurants: List<RestaurantModel>) {
        restaurants.forEach {
            val marker = MarkerOptions()
                .position(LatLng(it.location.latitude, it.location.longitude))
                .title(it.title)
            context?.let { notEmptyContext ->
                marker.icon(
                    bitmapDescriptorFromVector(
                        notEmptyContext,
                        R.drawable.ic_restaurant
                    )
                )
            }
            mMap.addMarker(marker).tag = it.id
        }
    }

    override fun showDialogRestaurantMiniInfo(clickedRestaurant: RestaurantModel) {
        val restaurantPopup = SimplePopupDialog().apply {
            listener = object : SimplePopupDialog.SimplePopupListeners {
                override fun onActionButton() {
                    mPresenter.onActionButtonRestaurantDialogClicked(clickedRestaurant)
                }

                override fun onCancel() {
                    dismiss()
                }
            }
        }
        fragmentManager?.let {
            restaurantPopup.initAndShowDialog(
                it,
                actionButtonName = resources.getString(R.string.map_restaurant_popup_action_button_title),
                dialogTitle = clickedRestaurant.title,
                message = clickedRestaurant.details
            )
        }
    }

    override fun showPopupNoDataFoundAboutMarker() {
        toast(R.string.map_restaurant_clicked_no_info_found)
    }

    override fun showPopupNoRestaurants() {
        toast(R.string.no_restaurants_loaded)
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

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
    //endregion
}