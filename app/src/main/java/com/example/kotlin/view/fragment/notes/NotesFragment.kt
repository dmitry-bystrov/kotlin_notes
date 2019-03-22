package com.example.kotlin.view.fragment.notes

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.example.kotlin.R
import com.example.kotlin.model.entity.Note
import com.example.kotlin.view.adapter.NotesAdapter
import com.example.kotlin.view.adapter.NotesViewHolder
import com.example.kotlin.view.base.BaseFragment
import com.example.kotlin.view.fragment.notes.NotesFragmentDirections.actionNotesToEditor
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.layout_notes_fragment.*

class NotesFragment : BaseFragment<List<Note>?, NotesViewState>() {
    private lateinit var adapter: NotesAdapter

    override val layoutRes: Int = R.layout.layout_notes_fragment

    override val viewModel: NotesViewModel by lazy {
        ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { findNavController(view).navigate(actionNotesToEditor()) }

        adapter = NotesAdapter(object : NotesViewHolder.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val actionEdit = actionNotesToEditor()
                actionEdit.noteId = note.id
                findNavController(view).navigate(actionEdit)
            }
        })

        rv_notes.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        MenuInflater(activity).inflate(R.menu.main, menu).let { true }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout -> logout()
        }

        return false
    }

    private fun logout() {
        activity?.let { context ->
            AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener {
                    view?.let { view ->
                        Navigation.findNavController(view)
                            .navigate(NotesFragmentDirections.actionNotesToSplash())
                    }
                }
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let { adapter.notes = it }
    }
}
