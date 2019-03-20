package com.example.kotlin.view.fragment.editor

import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class EditorViewModel(private val repository: KotlinRepository = KotlinRepository) :
    BaseViewModel<Note?, EditorViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let { repository.saveNote(it) }
    }

    fun loadNote(id: String) {
        repository.getNote(id).observeForever { t ->
            t?.let {
                when (it) {
                    is NoteResult.Success<*> -> {
                        viewStateLiveData.value = EditorViewState(note = it.data as? Note)
                    }
                    is NoteResult.Error -> {
                        viewStateLiveData.value = EditorViewState(error = it.error)
                    }
                }
            }
        }
    }
}
