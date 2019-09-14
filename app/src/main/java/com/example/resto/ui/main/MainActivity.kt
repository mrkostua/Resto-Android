package com.example.resto.ui.main

import com.example.resto.R
import com.example.resto.ui.BaseActivity
import com.example.resto.ui.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    override val layoutId: Int
        get() = R.layout.activity_main

    //region Init
    override fun init() {
        super.init()
        presenter.attachView(this)

        bottomNavigationBnb.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
    //endregion

    //region View
    override fun navigateToDashboard() {
        //TODO("not implemented")
    }

    override fun navigateToMap() {
        //TODO("not implemented")
    }

    override fun navigateToQrCode() {
        //TODO("not implemented")
    }
    //endregion
    //endregion

    private fun showFragment(tag: String, defaultFragment: () -> BaseFragment) {
        showFragment(R.id.frameContainerFl, tag, defaultFragment)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                presenter.onNavigationDashboardClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                presenter.onNavigationMapClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qr_code -> {
                presenter.onNavigationQrCodeClicked()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
