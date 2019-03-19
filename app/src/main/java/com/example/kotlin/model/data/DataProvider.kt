package com.example.kotlin.model.data

import android.arch.lifecycle.LiveData
import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note

interface DataProvider {
    fun subscribeToNotes(): LiveData<NoteResult>
    fun getNote(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}