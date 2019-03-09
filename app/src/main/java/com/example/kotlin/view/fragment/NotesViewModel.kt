package com.example.kotlin.view.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class NotesViewModel : BaseViewModel() {
    private val viewStateLiveData: MutableLiveData<NotesViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = NotesViewState(KotlinRepository.notes)
    }

    fun viewState(): LiveData<NotesViewState> = viewStateLiveData
}
