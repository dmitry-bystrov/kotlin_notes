package com.example.kotlin.root

import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IFragmentContainer {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showSuccessMessage(message: String) {
        showSnackbar(message, R.color.successColor)
    }

    override fun showErrorMessage(message: String) {
        showSnackbar(message, R.color.errorColor)
    }

    override fun showErrorMessage(error: Throwable) {
        error.message?.let { showSnackbar(it, R.color.errorColor) }
    }

    private fun showSnackbar(message: String, @ColorRes color: Int) {
        val snackbar = Snackbar.make(root_coordinator_layout, message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, color))
        snackbar.show()
    }
}
