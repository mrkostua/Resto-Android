package com.example.resto.util.injection.component

import android.app.Application
import com.example.resto.RestoApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.example.resto.util.injection.module.ActivityBindingModule
import com.example.resto.util.injection.module.AppModule
import com.example.resto.util.injection.module.ResourceProviderModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ResourceProviderModule::class,
        ActivityBindingModule::class]
)
interface AppComponent : AndroidInjector<RestoApp> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}