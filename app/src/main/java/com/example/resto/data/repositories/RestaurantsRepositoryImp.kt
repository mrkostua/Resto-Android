package com.example.resto.data.repositories

import com.example.resto.data.RestaurantModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RestaurantsRepositoryImp @Inject constructor() : RestaurantsRepository {

    override fun getAllRestaurants(): Single<List<RestaurantModel>> {
        return getMockedRestaurantsResponse()
    }


    //region mocked data
    private fun getMockedRestaurantsResponse(): Single<List<RestaurantModel>> {
        return Single.fromObservable(Observable.fromArray(getMockedRestaurants()))
    }

    private fun getMockedRestaurants(): List<RestaurantModel> {
        return listOf(
            RestaurantModel(1, LatLng(52.113168, 20.852531), "LemurBurger", "We have super nice burger."),
            RestaurantModel(2, LatLng(52.114960, 20.809292), "Lemur Retro Bar", "Check our menu, no regrets after."),
            RestaurantModel(3, LatLng(52.103426, 20.876862), "Lemur Vega Bar","For vegans and not only, welcome."),
            RestaurantModel(4, LatLng(52.183784, 20.953691), "Lemur Happy Day Restaurant","Be happy with your food, and people."),
            RestaurantModel(5, LatLng(52.222584, 21.026686), "Not a Bad Place","Wow"),
            RestaurantModel(6, LatLng(52.241929, 20.967661), "Don't worry Cafe","Coffee, tea welcome.")
        )
    }

    //
}