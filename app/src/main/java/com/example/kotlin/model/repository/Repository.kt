package com.example.kotlin.model.repository

import com.example.kotlin.model.data.DataProvider
import com.example.kotlin.model.entity.Note

class Repository(private val dataProvider: DataProvider) {
    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNote(id: String) = dataProvider.getNote(id)
    fun deleteNote(id: String) = dataProvider.deleteNote(id)
}