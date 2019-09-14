package com.example.resto.ui.main.fragments.map

import com.example.resto.data.RestaurantModel
import com.example.resto.ui.BaseFragmentContract

interface MapFragmentContract {
    interface View : BaseFragmentContract.View {
        fun centerOnUser(lat: Double, lon: Double)
        fun drawRestaurantsMarkersOnMap(restaurants: List<RestaurantModel>)
        fun showPopupNoRestaurants()
    }


    interface Presenter : BaseFragmentContract.Presenter<View> {
        fun onResumed()
        fun onPaused()
        fun onLocationButtonClicked()
        fun onMapReady()
    }
}