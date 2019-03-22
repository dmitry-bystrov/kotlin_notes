package com.example.kotlin.view.fragment.splash

import com.example.kotlin.model.errors.NoAuthException
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class SplashViewModel(private val repository: KotlinRepository = KotlinRepository) :
    BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }

}
