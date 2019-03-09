package com.example.kotlin.view.fragment.notes

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.repository.KotlinRepository
import com.example.kotlin.view.base.BaseViewModel

class EditroViewModel : BaseViewModel() {
    private val viewStateLiveData: MutableLiveData<EditorViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = EditorViewState(KotlinRepository.notes)
    }

    fun viewState(): LiveData<EditorViewState> = viewStateLiveData
}
