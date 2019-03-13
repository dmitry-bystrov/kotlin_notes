package com.example.kotlin.view.fragment.notes

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class NotesViewModel(private val repository: KotlinRepository = KotlinRepository) : BaseViewModel() {
    private val viewStateLiveData: MutableLiveData<NotesViewState> = MutableLiveData()

    init {
        repository.getNotes().observeForever {
            viewStateLiveData.value =
                    viewStateLiveData.value?.copy(notes = it!!) ?: NotesViewState(it!!)
        }

    }

    fun viewState(): LiveData<NotesViewState> = viewStateLiveData
}
