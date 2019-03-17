package com.example.kotlin.view.fragment.editor

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class EditorViewModel(private val repository: KotlinRepository = KotlinRepository) : BaseViewModel() {
    private val currentNote = MutableLiveData<Note>()

    val observableCurrentNote: LiveData<Note>
        get() = currentNote

    fun saveChanges(note: Note) {
        currentNote.value = note
    }

    fun initNote(id: String) {
        currentNote.value = repository.getNote(id)
    }

    override fun onCleared() {
        currentNote.value?.let { repository.saveNote(it) }
    }
}