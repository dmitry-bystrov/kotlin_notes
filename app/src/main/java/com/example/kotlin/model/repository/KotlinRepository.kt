package com.example.kotlin.model.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor
import java.util.*

object KotlinRepository {
    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes: MutableList<Note> = mutableListOf(
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

    init {
        notesLiveData.value = notes
    }

    fun getNote(id: String): Note? {
        for (i in 0 until notes.size) {
            if (notes[i].id == id) {
                return notes[i]
            }
        }

        return null
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in 0 until notes.size) {
            if (notes[i].id == note.id) {
                notes[i] = note
                return
            }
        }

        notes.add(note)
    }
}