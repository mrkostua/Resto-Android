package com.example.resto.util.managers

import android.location.Location
import io.reactivex.Observable

interface LocationManager {
    val locationUnavailable: Observable<Any>
    val lastLocation: Observable<Location>
    val isConnected: Boolean

    fun setLocationInterface(locationInterface: LocationManagerImpl.LocationManagerInterface): LocationManagerImpl
    fun checkLocationSettings()
    fun startLocationUpdates()
    fun stopLocationUpdates()
    fun connect()
}