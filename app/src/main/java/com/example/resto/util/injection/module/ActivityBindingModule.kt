package com.example.resto.util.injection.module

import com.example.resto.ui.main.MainActivity
import com.example.resto.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.resto.util.injection.scope.PerActivity

@Module
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity
}