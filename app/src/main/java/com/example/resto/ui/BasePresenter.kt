package com.example.resto.ui

abstract class BasePresenter<V: BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null


    override fun attachView(view: V) {
        this.view = view
    }


    override fun detachView() {
        view = null
    }


    override fun clear() {
        detachView()
    }
}