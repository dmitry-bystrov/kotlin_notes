package com.example.kotlin.view.fragment.notes

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.model.entity.Note
import com.example.kotlin.view.adapter.NotesAdapter
import com.example.kotlin.view.adapter.NotesViewHolder
import com.example.kotlin.view.adapter.SwipeToButtonCallback
import com.example.kotlin.view.base.BaseFragment
import com.example.kotlin.view.fragment.notes.NotesFragmentDirections.actionNotesToEditor
import com.example.kotlin.view.fragment.notes.delete.DeleteDialog
import com.example.kotlin.view.fragment.notes.logout.LogoutDialog
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.layout_notes_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotesFragment : BaseFragment<List<Note>?, NotesViewState>() {
    private lateinit var adapter: NotesAdapter

    override val layoutRes: Int = R.layout.layout_notes_fragment
    override val mModel: NotesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { findNavController(view).navigate(actionNotesToEditor()) }
        toolbar_action.setOnClickListener {
            val logoutDialog = LogoutDialog.newInstance()
            logoutDialog.logoutListener = logoutListener
            logoutDialog.show(childFragmentManager, logoutDialog.tag)
        }

        adapter = NotesAdapter(object : NotesViewHolder.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val actionEdit = actionNotesToEditor()
                actionEdit.noteId = note.id
                findNavController(view).navigate(actionEdit)
            }
        })

        rv_notes.adapter = adapter

        val swipeCallback = SwipeToButtonCallback(R.drawable.ic_delete, getAppCompatActivity())
        swipeCallback.swipeToButtonListener = swipeToButtonListener

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rv_notes)

        rv_notes.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeCallback.onDraw(c)
            }
        })

        mModel.observableDeleteStatus.observe(this, Observer {
            it?.let {
                getFragmentContainer().showSuccessMessage(getString(R.string.delete_note_success))
                mModel.clearDeleteStatus()
            }
        })
    }

    private val logoutListener = object : LogoutDialog.LogoutListener {
        override fun onLogout() {
            AuthUI.getInstance()
                .signOut(getAppCompatActivity())
                .addOnCompleteListener {
                    findNavController().navigate(NotesFragmentDirections.actionNotesToSplash())
                }
        }
    }

    private val swipeToButtonListener = object : SwipeToButtonCallback.SwipeToButtonListener {
        override fun onButtonClick(position: Int) {
            val deleteDialog = DeleteDialog.newInstance()
            deleteDialog.deleteListener = object : DeleteDialog.DeleteListener {
                override fun onDelete() {
                    mModel.deleteNote(adapter.notes[position].id)
                }
            }

            deleteDialog.show(childFragmentManager, deleteDialog.tag)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let { adapter.notes = it }
    }
}
