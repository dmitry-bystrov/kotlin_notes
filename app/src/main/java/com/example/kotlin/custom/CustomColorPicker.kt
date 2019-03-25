package com.example.kotlin.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.kotlin.R
import com.example.kotlin.extensions.getColorInt
import com.example.kotlin.model.entity.NoteColor

class CustomColorPicker : LinearLayout {

    var onColorClickListener: (color: NoteColor) -> Unit = {}

    val isOpen: Boolean
        get() = measuredHeight > 0

    private var desiredHeight = 0

    private val animator by lazy {
        ValueAnimator().apply {
            duration = 250L
            addUpdateListener(updateListener)
        }
    }

    private val updateListener by lazy {
        ValueAnimator.AnimatorUpdateListener { animator ->
            layoutParams.apply {
                height = animator.getAnimatedValue("height") as Int
            }.let { layoutParams = it }

            val scaleFactor = animator.getAnimatedValue("scale") as Float
            for (i in 0 until childCount) {
                getChildAt(i).apply {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    alpha = scaleFactor
                }
            }
        }
    }

    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("InflateParams")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        NoteColor.values().forEach { color ->

            val circleView = LayoutInflater.from(context).inflate(R.layout.color_circle, null)
            val circleImage = circleView.findViewById<ImageView>(R.id.circle)
            circleImage.apply {
                context?.let { imageTintList = ColorStateList.valueOf(color.getColorInt(context)) }
                setOnClickListener { onColorClickListener(color) }
            }

            addView(circleView)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        layoutParams.apply {
            desiredHeight = height
            height = 0
        }.let { layoutParams = it }
    }

    fun open() {
        animator.cancel()
        animator.setValues(
            PropertyValuesHolder.ofInt("height", measuredHeight, desiredHeight),
            PropertyValuesHolder.ofFloat("scale", getChildAt(0).scaleX, 1f)
        )
        animator.start()
    }

    fun close() {
        animator.cancel()
        animator.setValues(
            PropertyValuesHolder.ofInt("height", measuredHeight, 0),
            PropertyValuesHolder.ofFloat("scale", getChildAt(0).scaleX, 0f)
        )
        animator.start()
    }
}