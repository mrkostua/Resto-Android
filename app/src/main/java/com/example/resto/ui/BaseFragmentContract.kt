package com.example.resto.ui

interface BaseFragmentContract {

    interface View {
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter<in V : View> {
        fun attachView(view: V)
        fun detachView()
        fun clear()
    }
}