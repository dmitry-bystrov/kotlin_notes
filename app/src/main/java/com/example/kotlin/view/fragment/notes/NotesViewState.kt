package com.example.kotlin.view.fragment.notes

import com.example.kotlin.model.entity.Note
import com.example.kotlin.view.base.BaseViewState

class NotesViewState(notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)