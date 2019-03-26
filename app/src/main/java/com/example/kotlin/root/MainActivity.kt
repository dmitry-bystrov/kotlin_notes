package com.example.kotlin.root

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlin.R
import com.example.kotlin.custom.SimpleAnimatorListener
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

    override fun showLoadingSpinner(show: Boolean) {
        progressbar_view.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showSuccessMessage(message: String) {
        showPopupMessage(message, R.color.successColor)
    }

    override fun showErrorMessage(message: String) {
        showPopupMessage(message, R.color.errorColor)
    }

    override fun showErrorMessage(error: Throwable) {
        error.message?.let { showPopupMessage(it, com.example.kotlin.R.color.errorColor) }
    }

    private fun showPopupMessage(message: String, @ColorRes color: Int) {
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        val messageCard = layoutInflater.inflate(R.layout.card_view_message, rootView, false)
        val messageText = messageCard.findViewById<TextView>(R.id.tv_message)
        rootView.addView(messageCard)

        messageCard.setBackgroundColor(compatColor(color))
        messageText.text = message

        val size = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 94f,
            resources.displayMetrics
        )

        val startAnimation = ObjectAnimator.ofFloat(messageCard, View.TRANSLATION_Y, size)
        startAnimation.duration = 500
        startAnimation.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationStart(animation: Animator?) {
                messageCard.translationY = -size
                messageCard.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                val endAnimation = ObjectAnimator.ofFloat(messageCard, View.TRANSLATION_Y, -size)
                endAnimation.setDuration(500).startDelay = 1500
                endAnimation.addListener(object : SimpleAnimatorListener() {
                    override fun onAnimationEnd(animation: Animator?) {
                        messageCard.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        messageCard.visibility = View.GONE
                    }
                })

                endAnimation.start()
            }
        })

        startAnimation.start()
    }

    private fun compatColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(this, color)
    }
}
