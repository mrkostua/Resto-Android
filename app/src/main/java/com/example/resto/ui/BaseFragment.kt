package com.example.resto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), BaseFragmentContract.View {

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    protected open fun init() = Unit

    override fun onDestroyView() {
        clear()
        super.onDestroyView()
    }

    protected open fun clear() = Unit

    override fun showProgress() = Unit
    override fun hideProgress() = Unit
}