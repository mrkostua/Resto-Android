package com.example.resto.ui.main

import com.example.resto.ui.BasePresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor() : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    override fun onNavigationDashboardClicked() {
        view?.navigateToDashboard()
    }

    override fun onNavigationMapClicked() {
        view?.navigateToMap()
    }

    override fun onNavigationQrCodeClicked() {
        view?.navigateToQrCode()
    }

}