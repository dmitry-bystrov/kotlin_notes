package com.example.kotlin.view.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {

    open val dataIsLoading = MutableLiveData<Boolean>()

    fun getLoadingState(): LiveData<Boolean> = dataIsLoading

    open val viewStateLiveData = MutableLiveData<S>()

    open fun getViewState(): LiveData<S> = viewStateLiveData
}