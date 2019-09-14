package com.example.resto.ui.main.fragments.qrCode

import com.example.resto.ui.BaseFragmentContract

interface QrCodeFragmentContract {
    interface View : BaseFragmentContract.View {
    }


    interface Presenter : BaseFragmentContract.Presenter<View> {
    }
}