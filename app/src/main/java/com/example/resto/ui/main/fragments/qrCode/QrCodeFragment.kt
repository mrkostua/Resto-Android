package com.example.resto.ui.main.fragments.qrCode

import android.graphics.Paint
import com.example.resto.R
import com.example.resto.data.QrCodeBodyModel
import com.example.resto.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_qr_code.*
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

    private val mockedQrCodeBody = QrCodeBodyModel(1, 1, 1)

    //region init
    override fun init() {
        mPresenter.attachView(this)
        initViews()
    }

    private fun initViews() {
        setNoPersonalTvUnderlined()
        scanButtonTv.setOnClickListener {
            navigateToCreateOrderActivity(mockedQrCodeBody)
        }
    }
    //endregion

    private fun setNoPersonalTvUnderlined() {
        with(qrCodeAskForPersonalTv) {
            paintFlags = (paintFlags or Paint.UNDERLINE_TEXT_FLAG)
        }
    }

    private fun navigateToCreateOrderActivity(qrCodeBody: QrCodeBodyModel) {
        //TODO navigate to new Activity and to Bundle to Intent
    }
}