package com.example.resto.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import dagger.android.support.DaggerAppCompatActivity
import com.example.resto.util.extensions.findFragmentByTag
import com.example.resto.util.extensions.replaceFragment
import kotlin.reflect.KClass

abstract class BaseActivity : DaggerAppCompatActivity(), BaseActivityContract.View {


    abstract val layoutId: Int

    //region Init
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        init()
    }


    protected open fun init() = Unit
    //endregion


    fun navigateToActivity(activityClass: KClass<*>, flags: List<Int>? = null) {
        val intent = Intent(this, activityClass.java)
        flags?.forEach { intent.addFlags(it) }
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    fun showFragment(@IdRes containerId: Int, tag: String, defaultFragment: () -> BaseFragment) {
        val fragment = supportFragmentManager.findFragmentByTag(tag) { defaultFragment.invoke() }
        replaceFragment(containerId, fragment, tag)
    }

    fun showFragment(
        @IdRes containerId: Int, tag: String,
        defaultFragment: () -> BaseFragment,
        addToBackStack: Boolean
    ) {
        val fragment = supportFragmentManager.findFragmentByTag(tag, defaultFragment)
        if (addToBackStack) {
            supportFragmentManager
                .beginTransaction()
                .add(containerId, fragment, fragment.tag)
                .addToBackStack(fragment.tag)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .add(containerId, fragment, fragment.tag)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    protected open fun clear() {}

}




