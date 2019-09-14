package com.example.resto.ui

interface BaseContract {

    interface View


    interface Presenter<in V: View> {
        fun attachView(view: V)
        fun detachView()
        fun clear()
    }
}