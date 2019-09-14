package com.example.resto.ui.main.fragments.qrCode

import com.example.resto.util.injection.scope.PerFragment
import dagger.Binds
import dagger.Module

@Module
abstract class QrCodeFragmentModule {
    @Binds
    @PerFragment
    abstract fun providePresenter(presenter: QrCodeFragmentPresenter): QrCodeFragmentContract.Presenter
}
