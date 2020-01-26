package com.example.kotlin.root

interface IFragmentContainer {
    fun showSuccessMessage(message: String)

    fun showErrorMessage(message: String)

    fun showErrorMessage(error: Throwable)

    fun showLoadingSpinner(show: Boolean)
}