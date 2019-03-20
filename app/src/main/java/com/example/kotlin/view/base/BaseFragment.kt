package com.example.kotlin.view.base

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin.root.IFragmentContainer

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutRes, container, false)

        viewModel.getViewState().observe(this, Observer<S> { t ->
            t?.let {
                it.data?.let { renderData(t.data) } ?: it.error?.let { renderError(t.error!!) }
            }
        })

        return view
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        getFragmentContainer().showErrorMessage(error)
    }

    fun getFragmentContainer() = (activity as IFragmentContainer)

    fun getAppCompatActivity() = (activity as AppCompatActivity)

    fun setSupportActionBar(toolbar: Toolbar) {
        getAppCompatActivity().setSupportActionBar(toolbar)
    }

    fun getSupportActionBar(): ActionBar? {
        return getAppCompatActivity().supportActionBar
    }
}