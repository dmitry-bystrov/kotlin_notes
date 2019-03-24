package com.example.kotlin.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.util.TypedValue
import android.view.MotionEvent

private const val DEFAULT_MARGIN = 12

class SwipeToButtonCallback(@DrawableRes buttonDrawableRes: Int, context: Context) : Callback() {
    private val buttonLeftMargin: Int
    private var swipeBack: Boolean = false
    private var buttonShowed: Boolean = false
    private val buttonInstance: RectF
    private val buttonDrawable: Drawable? = context.resources.getDrawable(buttonDrawableRes, null)
    private var currentViewHolder: RecyclerView.ViewHolder? = null
    private val buttonWidth: Int
        get() = buttonDrawable!!.bounds.width() + buttonLeftMargin

    var swipeToButtonListener: SwipeToButtonListener? = null

    init {
        buttonDrawable?.setBounds(0, 0, buttonDrawable.intrinsicWidth, buttonDrawable.intrinsicHeight)
        this.buttonInstance = RectF()
        this.buttonLeftMargin = Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_MARGIN.toFloat(),
                context.resources.displayMetrics
            )
        )
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(0, LEFT)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = buttonShowed
            return 0
        }

        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowed) {
                val dXShowed = Math.min(dX, (-1 * buttonWidth).toFloat())
                super.onChildDraw(c, recyclerView, viewHolder, dXShowed, dY, actionState, isCurrentlyActive)
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        if (!buttonShowed) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        currentViewHolder = viewHolder
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            swipeBack = event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP

            if (swipeBack) {
                if (dX < -1 * buttonWidth) buttonShowed = true

                if (buttonShowed) {
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    setItemsClickable(recyclerView, false)
                }
            }

            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchUpListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                super@SwipeToButtonCallback.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    0f,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                recyclerView.setOnTouchListener { _, _ -> false }
                setItemsClickable(recyclerView, true)
                swipeBack = false

                if (buttonInstance.contains(event.x, event.y)) {
                    if (buttonShowed) {
                        swipeToButtonListener?.onButtonClick(viewHolder.adapterPosition)
                    }
                }

                buttonShowed = false
                currentViewHolder = null
            }

            false
        }
    }

    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height.toFloat()
        val buttonWidth = buttonDrawable!!.bounds.width().toFloat()
        val buttonHeight = buttonDrawable.bounds.height().toFloat()
        val buttonLeft = (itemView.right - buttonDrawable.bounds.width()).toFloat()
        val buttonTop = itemView.top + itemHeight / 2 - buttonHeight / 2
        buttonInstance.set(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight)

        c.save()
        c.translate(buttonLeft, buttonTop)
        buttonDrawable.draw(c)
        c.restore()
    }

    fun onDraw(c: Canvas) {
        currentViewHolder?.let { drawButtons(c, it) }
    }

    interface SwipeToButtonListener {
        fun onButtonClick(position: Int)
    }
}