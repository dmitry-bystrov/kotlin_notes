package com.example.kotlin.view.fragment.editor

import com.example.kotlin.model.entity.Note
import com.example.kotlin.view.base.BaseViewState

class EditorViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)