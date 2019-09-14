package com.example.resto.ui.main.fragments.map

import com.example.resto.data.repositories.RestaurantsRepository
import com.example.resto.data.repositories.RestaurantsRepositoryImp
import com.example.resto.util.injection.scope.PerFragment
import com.example.resto.util.managers.LocationManager
import dagger.Binds
import dagger.Module
import com.example.resto.util.managers.LocationManagerImpl

@Module
abstract class MapFragmentModule {
    @Binds
    @PerFragment
    abstract fun providePresenter(presenter: MapFragmentPresenter): MapFragmentContract.Presenter


    @Binds
    @PerFragment
    abstract fun provideLocationManager(managerImpl: LocationManagerImpl): LocationManager

    @Binds
    @PerFragment
    abstract fun provideRestaurantsRepository(restaurantsRepository: RestaurantsRepositoryImp): RestaurantsRepository
}