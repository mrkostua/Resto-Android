package com.example.resto.ui.main

import com.example.resto.ui.main.fragments.map.MapFragmentModule
import com.example.resto.util.injection.scope.PerActivity
import com.example.resto.util.injection.scope.PerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.resto.ui.main.fragments.map.MapFragment
import com.example.resto.ui.main.fragments.qrCode.QrCodeFragment
import com.example.resto.ui.main.fragments.qrCode.QrCodeFragmentModule


@Module
abstract class MainActivityModule {
    @PerActivity
    @Binds
    abstract fun providePresenter(presenter: MainActivityPresenter): MainActivityContract.Presenter

    @PerFragment
    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    abstract fun mapFragment(): MapFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [QrCodeFragmentModule::class])
    abstract fun qrCodeFragment(): QrCodeFragment
}