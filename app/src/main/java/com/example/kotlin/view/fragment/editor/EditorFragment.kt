package com.example.kotlin.view.fragment.editor

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.View
import androidx.appcompat.widget.Toolbar
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
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class EditorFragment : BaseFragment<Note?, EditorViewState>() {

    private lateinit var toolbar: Toolbar
    private var currentNote = Note()
    private var noteColor = NoteColor.ORANGE

    override val layoutRes: Int = R.layout.layout_editor_fragment
    override val mModel: EditorViewModel by viewModel()

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
            if (noteId == DEFAULT_NOTE_ID) renderData(null) else mModel.loadNote(noteId)
        }

        color_picker.onColorClickListener = {
            noteColor = it
            setToolbarColor(it)
            saveChanges()
        }

        setEditListener()
    }

    private fun setEditListener() {
        et_title.addTextChangedListener(textChangeWatcher)
        et_content.addTextChangedListener(textChangeWatcher)
    }

    private fun removeEditListener() {
        et_title.removeTextChangedListener(textChangeWatcher)
        et_content.removeTextChangedListener(textChangeWatcher)
    }

    private fun setToolbarTitle() {
        toolbar_title?.text = when {
            currentNote.content.isNotEmpty() -> currentNote.lastChanged.format()
            else -> getString(R.string.new_note_title)
        }
    }

    private fun updateViewState() {
        removeEditListener()
        et_title.setText(currentNote.title)
        et_content.setText(currentNote.content)
        setToolbarColor(currentNote.color)
        setEditListener()
    }

    private fun setToolbarColor(color: NoteColor) {
        toolbar_underline.setBackgroundColor(color.getColorInt(getAppCompatActivity()))
    }

    override fun renderData(data: Note?) {
        if (currentNote.title.isEmpty() && currentNote.content.isEmpty() && data != null) {
            currentNote = data
            setToolbarTitle()
            updateViewState()
        }
    }

    private fun saveChanges() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) return

        Handler().postDelayed({
            currentNote = currentNote.copy(
                title = et_title?.text.toString(),
                content = et_content?.text.toString(),
                color = noteColor,
                lastChanged = Date()
            )

            mModel.saveChanges(currentNote)

        }, EDITOR_SAVE_DELAY)
    }
}
