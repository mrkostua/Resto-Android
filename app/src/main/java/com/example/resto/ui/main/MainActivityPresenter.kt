package com.example.resto.ui.main

import com.example.resto.ui.BasePresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor() : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    override fun attachView(view: MainActivityContract.View) {
        super.attachView(view)
        view.checkPermissions()
    }

    override fun onNavigationDashboardClicked() {
        view?.navigateToDashboard()
    }

    override fun onNavigationMapClicked() {
        view?.navigateToMap()
    }

    override fun onNavigationQrCodeClicked() {
        view?.navigateToQrCode()
    }

    override fun onPermissionsChecked(permissions: List<String>) {
        when {
            permissions.isNotEmpty() -> view?.requestPermissions(permissions)
        }
    }
}