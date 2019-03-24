package com.example.kotlin.model.data

import android.arch.lifecycle.LiveData
import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.User

interface DataProvider {
    fun getCurrentUser(): LiveData<User?>
    fun subscribeToNotes(): LiveData<NoteResult>
    fun getNote(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun deleteNote(id: String): LiveData<NoteResult>
}