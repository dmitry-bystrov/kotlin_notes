package com.example.kotlin.view.fragment.splash

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kotlin.R
import com.example.kotlin.view.base.BaseFragment

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {
    override val layoutRes: Int = R.layout.layout_splash_fragment

    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let { navigateToNotes() }
    }

    private fun navigateToNotes() {
        view?.let {
            findNavController().navigate(SplashFragmentDirections.actionSplashToNotes())
        }
    }
}
