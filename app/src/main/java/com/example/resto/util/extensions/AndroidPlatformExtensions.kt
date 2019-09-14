package com.example.resto.util.extensions

import android.app.Activity
import android.content.Intent
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.resto.ui.BaseFragment
import kotlin.reflect.KClass

fun Activity.navigateToActivity(
    activityClass: KClass<*>,
    flags: List<Int>? = null) {
    val intent = Intent(this, activityClass.java)
    flags?.forEach { intent.addFlags(it) }
    startActivity(intent)
}

fun FragmentManager.changeFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction
        .replace(containerViewId, fragment, tag)
        .commit()
    executePendingTransactions()
}

fun FragmentManager.findFragmentByTag(tag: String, defaultValue: () -> BaseFragment) =
        findFragmentByTag(tag) as? BaseFragment ?: defaultValue.invoke()

fun AppCompatActivity.replaceFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment,
    tag: String) = supportFragmentManager.changeFragment(containerViewId, fragment, tag)
