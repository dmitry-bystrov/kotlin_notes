package com.example.kotlin.view.fragment.editor

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.kotlin.R
import com.example.kotlin.custom.CustomTextWatcher
import com.example.kotlin.extensions.DATE_TIME_FORMAT
import com.example.kotlin.extensions.EDITOR_SAVE_DELAY
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor
import com.example.kotlin.view.base.BaseFragment
import kotlinx.android.synthetic.main.layout_editor_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class EditorFragment : BaseFragment() {
    companion object {
        fun newInstance() = EditorFragment()
    }

    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: EditorViewModel
    private lateinit var note: Note

    private val textChangeWatcher = object : CustomTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            saveChanges()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_editor_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)

        arguments?.let {
            val safeArgs = EditorFragmentArgs.fromBundle(it)
            viewModel.initNote(safeArgs.noteId)
        }

        viewModel.observableCurrentNote.value.let {
            note = it ?: Note()
            setToolbar(it)
            updateViewState()
        }
    }

    private fun setToolbar(note: Note?) {
        toolbar = view!!.findViewById(R.id.toolbar)

        val actionBar = getSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { Navigation.findNavController(it).popBackStack() }

        actionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }
    }

    private fun updateViewState() {
        et_title.setText(note.title)
        et_content.setText(note.content)

        val color = when (note.color) {
            NoteColor.ORANGE -> R.color.orange
            NoteColor.BROWN -> R.color.brown
            NoteColor.GREEN -> R.color.green
            NoteColor.CYAN -> R.color.cyan
            NoteColor.PURPLE -> R.color.purple
            NoteColor.PINK -> R.color.pink
            NoteColor.LIME -> R.color.lime
            NoteColor.RED -> R.color.red
        }

        toolbar_underline.setBackgroundColor(ContextCompat.getColor(getAppCompatActivity(), color))
        et_title.addTextChangedListener(textChangeWatcher)
        et_content.addTextChangedListener(textChangeWatcher)
    }

    private fun saveChanges() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) return

        Handler().postDelayed({
            note = note.copy(
                title = et_title.text.toString(),
                content = et_content.text.toString(),
                lastChanged = Date()
            )

            viewModel.saveChanges(note)

        }, EDITOR_SAVE_DELAY)
    }
}
