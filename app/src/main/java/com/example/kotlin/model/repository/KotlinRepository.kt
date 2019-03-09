package com.example.kotlin.model.repository

import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor

object KotlinRepository {
    val notes: List<Note> = listOf(
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.BROWN
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.CYAN
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.GREEN
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.PINK
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.LIME
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.PURPLE
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык", NoteColor.RED
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            "Моя первая заметка", "Kotlin очень краткий, но при этом выразительный язык"
        )
    )
}