package com.example.kotlin.model.repository

import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor
import java.util.*

object KotlinRepository {
    val notes: List<Note> = listOf(
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.BROWN
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.CYAN
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.GREEN
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.PINK
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.LIME
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.PURPLE
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.RED
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык"
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.LIME
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Моя первая заметка",
            content = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.LIME
        )
    )
}