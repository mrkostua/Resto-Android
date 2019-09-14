package com.example.resto.data.repositories

import com.example.resto.data.RestaurantModel
import io.reactivex.Single

interface RestaurantsRepository {
    fun getAllRestaurants(): Single<List<RestaurantModel>>
}