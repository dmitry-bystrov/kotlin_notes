package com.example.kotlin.view.fragment.editor

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.kotlin.R
import com.example.kotlin.custom.SimpleTextWatcher
import com.example.kotlin.extensions.DEFAULT_NOTE_ID
import com.example.kotlin.extensions.EDITOR_SAVE_DELAY
import com.example.kotlin.extensions.format
import com.example.kotlin.extensions.getColorInt
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor
import com.example.kotlin.view.base.BaseFragment
import kotlinx.android.synthetic.main.layout_editor_fragment.*
import java.util.*

class EditorFragment : BaseFragment<Note?, EditorViewState>() {

    private lateinit var toolbar: Toolbar

    private lateinit var note: Note

    private var noteColor = NoteColor.ORANGE

    override val layoutRes: Int = R.layout.layout_editor_fragment

    override val viewModel: EditorViewModel by lazy {
        ViewModelProvider(this).get(EditorViewModel::class.java)
    }

    private val textChangeWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            saveChanges()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar_navigation.setOnClickListener { Navigation.findNavController(it).popBackStack() }
        toolbar_action.setOnClickListener {
            if (color_picker.isOpen) {
                color_picker.close()
            } else {
                color_picker.open()
            }
        }

        arguments?.let {
            val noteId = EditorFragmentArgs.fromBundle(it).noteId
            if (noteId == DEFAULT_NOTE_ID) renderData(null) else viewModel.loadNote(noteId)
        }

        color_picker.onColorClickListener = {
            noteColor = it
            setToolbarColor(it)
            saveChanges()
        }
    }

    private fun setEditListener() {
        et_title.addTextChangedListener(textChangeWatcher)
        et_content.addTextChangedListener(textChangeWatcher)
    }

    private fun removeEditListener() {
        et_title.removeTextChangedListener(textChangeWatcher)
        et_content.removeTextChangedListener(textChangeWatcher)
    }

    private fun setToolbarTitle(note: Note?) {
        toolbar_title?.text = note?.lastChanged?.format() ?: getString(R.string.new_note_title)
    }

    private fun updateViewState() {
        removeEditListener()
        et_title.setText(note.title)
        et_content.setText(note.content)
        setToolbarColor(note.color)
        setEditListener()
    }

    private fun setToolbarColor(color: NoteColor) {
        toolbar_underline.setBackgroundColor(color.getColorInt(getAppCompatActivity()))
    }

    override fun renderData(data: Note?) {
        this.note = data ?: Note()
        setToolbarTitle(data)
        updateViewState()
    }

    private fun saveChanges() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) return

        Handler().postDelayed({
            note = note.copy(
                title = et_title?.text.toString(),
                content = et_content?.text.toString(),
                color = noteColor,
                lastChanged = Date()
            )

            viewModel.saveChanges(note)

        }, EDITOR_SAVE_DELAY)
    }
}
