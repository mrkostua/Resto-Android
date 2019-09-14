package com.example.resto.ui

import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragmentPresenter<V: BaseFragmentContract.View> : BaseFragmentContract.Presenter<V> {
    protected var view: V? = null
    protected var compositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }


    override fun detachView() {
        view = null
    }


    override fun clear() {
        detachView()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}