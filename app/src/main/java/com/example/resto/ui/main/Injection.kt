package com.example.resto.ui.main

import com.example.resto.util.injection.scope.PerActivity
import dagger.Binds
import dagger.Module


@Module
abstract class MainActivityModule {
    @PerActivity
    @Binds
    abstract fun providePresenter(presenter: MainActivityPresenter): MainActivityContract.Presenter
}