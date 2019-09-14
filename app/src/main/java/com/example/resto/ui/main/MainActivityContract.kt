package com.example.resto.ui.main

import com.example.resto.ui.BaseActivityContract

interface MainActivityContract {
    interface View : BaseActivityContract.View {
        fun navigateToMap()
        fun navigateToDashboard()
        fun navigateToQrCode()
    }

    interface Presenter : BaseActivityContract.Presenter<View> {
        fun onNavigationDashboardClicked()
        fun onNavigationMapClicked()
        fun onNavigationQrCodeClicked()
    }
}