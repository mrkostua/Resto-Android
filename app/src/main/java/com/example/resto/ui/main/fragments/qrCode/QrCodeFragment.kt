package com.example.resto.ui.main.fragments.qrCode

import com.example.resto.R
import com.example.resto.ui.BaseFragment
import javax.inject.Inject

class QrCodeFragment : BaseFragment(), QrCodeFragmentContract.View {
    companion object {
        @JvmStatic
        val TAG = "com.example.resto.ui.main.fragments.qrCode.QrCodeFragment"

        fun newInstance(): QrCodeFragment {
            return QrCodeFragment()
        }
    }

    @Inject
    internal lateinit var mPresenter: QrCodeFragmentContract.Presenter

    override val layoutId: Int = R.layout.fragment_qr_code

    //region init
    override fun init() {
        mPresenter.attachView(this)
    }

    //endregion
}