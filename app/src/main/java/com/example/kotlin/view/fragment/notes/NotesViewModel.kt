package com.example.kotlin.view.fragment.notes

import androidx.lifecycle.Observer
import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.api.NoteResult.Error
import com.example.kotlin.model.api.NoteResult.Success
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class NotesViewModel(repository: KotlinRepository = KotlinRepository) :
    BaseViewModel<List<Note>?, NotesViewState>() {

    private val notesObserver = Observer<NoteResult> { t ->
        t?.let {
            when (it) {
                is Success<*> -> {
                    val checkedList = it.data as? List<*>
                    viewStateLiveData.value = NotesViewState(notes = checkedList?.filterIsInstance<Note>())
                }
                is Error -> {
                    viewStateLiveData.value = NotesViewState(error = it.error)
                }
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = NotesViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}
