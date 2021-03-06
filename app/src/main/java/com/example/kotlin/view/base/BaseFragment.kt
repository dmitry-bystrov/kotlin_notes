package com.example.kotlin.view.base

import android.app.Activity
import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin.R
import com.example.kotlin.model.errors.NoAuthException
import com.example.kotlin.root.IFragmentContainer
import com.firebase.ui.auth.AuthUI

private const val RC_SIGN_IN = 458

abstract class BaseFragment<T, S : BaseViewState<T>> : androidx.fragment.app.Fragment() {
    abstract val mModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutRes, container, false)

        mModel.getViewState().observe(this, Observer<S> { t ->
            t?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
            }
        })

        mModel.getLoadingState().observe(this, Observer {
            it?.let { loadingState ->
                getFragmentContainer().showLoadingSpinner(loadingState)
            }
        })

        return view
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> getFragmentContainer().showErrorMessage(error)
        }
    }

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            activity?.finish()
        }
    }

    fun getFragmentContainer() = (activity as IFragmentContainer)

    fun getAppCompatActivity() = (activity as AppCompatActivity)
}