package com.example.kotlin.view.fragment.splash

import android.arch.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.kotlin.R
import com.example.kotlin.view.base.BaseFragment

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {

    override val layoutRes: Int = R.layout.layout_splash_fragment

    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let { navigateToNotes() }
    }

    private fun navigateToNotes() {
        view?.let { Navigation.findNavController(it).navigate(SplashFragmentDirections.actionSplashToNotes()) }
    }
}
