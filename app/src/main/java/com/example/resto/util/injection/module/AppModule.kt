package com.example.resto.util.injection.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
abstract class AppModule {
    @Binds
    abstract fun context(application: Application): Context

    @Module
    companion object {

        @Provides
        fun provideCompositeSubscription(): CompositeDisposable {
            return CompositeDisposable()
        }
    }
}