package com.example.resto.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.resto.R
import com.example.resto.ui.BaseActivity
import com.example.resto.ui.BaseFragment
import com.example.resto.ui.main.fragments.map.MapFragment
import com.example.resto.ui.main.fragments.qrCode.QrCodeFragment
import com.example.resto.util.config.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    lateinit var mPresenter: MainActivityContract.Presenter

    override val layoutId: Int
        get() = R.layout.activity_main

    //region Init
    override fun init() {
        super.init()
        mPresenter.attachView(this)

        bottomNavigationBnb.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
    //endregion

    //region View
    override fun navigateToDashboard() {
        //TODO("not implemented")
    }

    override fun navigateToMap() {
        showFragment(MapFragment.TAG) { MapFragment.newInstance() }
    }

    override fun navigateToQrCode() {
        showFragment(QrCodeFragment.TAG) { QrCodeFragment.newInstance() }

    }

    override fun checkPermissions() {
        val permissions = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CALL_PHONE)
        }
        mPresenter.onPermissionsChecked(permissions)
    }

    override fun requestPermissions(permissions: List<String>) {
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
    }

    //endregion

    private fun showFragment(tag: String, defaultFragment: () -> BaseFragment) {
        showFragment(R.id.frameContainerFl, tag, defaultFragment)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                mPresenter.onNavigationDashboardClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                mPresenter.onNavigationMapClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qr_code -> {
                mPresenter.onNavigationQrCodeClicked()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
