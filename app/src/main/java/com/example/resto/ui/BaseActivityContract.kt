package com.example.resto.ui

interface BaseActivityContract : BaseContract {

    interface View : BaseContract.View

    interface Presenter<in V : View> : BaseContract.Presenter<V>
}