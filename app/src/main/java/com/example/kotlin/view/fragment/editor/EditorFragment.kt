package com.example.kotlin.view.fragment.editor

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.kotlin.R
import com.example.kotlin.extensions.DATE_TIME_FORMAT
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor
import com.example.kotlin.view.base.BaseFragment
import com.example.kotlin.view.fragment.notes.EditroViewModel
import kotlinx.android.synthetic.main.item_note.*
import java.text.SimpleDateFormat
import java.util.*

class EditorFragment : BaseFragment() {
    companion object {
        fun newInstance(note: Note?) {
            val instance: EditorFragment = EditorFragment()
            instance.note = note
        }
    }

    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: EditroViewModel
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_editor_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)

        val actionBar = getSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        viewModel = ViewModelProviders.of(this).get(EditroViewModel::class.java)
        updateViewState()
    }

    private fun updateViewState() {
        if (note != null) {
            tv_title.text = note?.title ?: ""
            tv_content.text = note?.content ?: ""
            val color = when (note!!.color) {
                NoteColor.ORANGE -> R.color.orange
                NoteColor.BROWN -> R.color.brown
                NoteColor.GREEN -> R.color.green
                NoteColor.CYAN -> R.color.cyan
                NoteColor.PURPLE -> R.color.purple
                NoteColor.PINK -> R.color.pink
                NoteColor.LIME -> R.color.lime
                NoteColor.RED -> R.color.red
            }

            toolbar.setBackgroundColor(ContextCompat.getColor(getAppCompatActivity(), color))
        }
    }
}
