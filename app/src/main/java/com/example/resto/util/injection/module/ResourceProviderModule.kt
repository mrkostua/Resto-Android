package com.example.resto.util.injection.module

import com.example.resto.util.config.ResourceProvider
import com.example.resto.util.config.ResourceProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ResourceProviderModule {

    @Binds
    abstract fun provideResourceProvider(resourceProviderImpl: ResourceProviderImpl): ResourceProvider
}