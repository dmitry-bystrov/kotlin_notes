package com.example.kotlin.model.entity

import java.util.*

class Note(
    val id: String,
    val title: String,
    val content: String,
    val color: NoteColor = NoteColor.ORANGE,
    val lastChanged: Date = Date()
) {
}

enum class NoteColor {
    BROWN,
    ORANGE,
    GREEN,
    CYAN,
    PURPLE,
    PINK,
    LIME,
    RED
}