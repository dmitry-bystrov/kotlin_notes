package com.example.kotlin.view.fragment.editor

import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.repository.Repository
import com.example.kotlin.view.base.BaseViewModel

class EditorViewModel(private val repository: Repository) :
    BaseViewModel<Note?, EditorViewState>() {

    fun saveChanges(note: Note) {
        viewStateLiveData.value = EditorViewState(note = note)
    }

    override fun onCleared() {
        viewStateLiveData.value?.data?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(id: String) {
        dataIsLoading.value = true
        repository.getNote(id).observeForever { t ->
            t?.let {
                dataIsLoading.value = false
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
