package com.example.kotlin.model.repository

import com.example.kotlin.model.data.DataProvider
import com.example.kotlin.model.data.FireStoreProvider
import com.example.kotlin.model.entity.Note

object KotlinRepository {
    private val dataProvider: DataProvider = FireStoreProvider()

    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNote(id: String) = dataProvider.getNote(id)
}