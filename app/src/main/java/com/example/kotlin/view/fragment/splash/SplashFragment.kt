package com.example.kotlin.view.fragment.splash

import androidx.navigation.fragment.findNavController
import com.example.kotlin.R
import com.example.kotlin.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {
    override val layoutRes: Int = R.layout.layout_splash_fragment
    override val mModel: SplashViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        mModel.requestUser()
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
