package com.example.kotlin.extensions

import android.content.Context
import android.support.v4.content.ContextCompat
import com.example.kotlin.R
import com.example.kotlin.model.entity.NoteColor
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd MMM yyyy HH:mm"
const val EDITOR_SAVE_DELAY = 500L
const val DEFAULT_NOTE_ID = "0"

fun Date.format(): String =
    SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        .format(this)

fun NoteColor.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, getColorRes()
    )

fun NoteColor.getColorRes(): Int = when (this) {
    NoteColor.ORANGE -> R.color.orange
    NoteColor.BROWN -> R.color.brown
    NoteColor.GREEN -> R.color.green
    NoteColor.CYAN -> R.color.cyan
    NoteColor.PURPLE -> R.color.purple
    NoteColor.PINK -> R.color.pink
    NoteColor.LIME -> R.color.lime
    NoteColor.RED -> R.color.red
}