package com.example.kotlin.view.fragment.notes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin.MainActivity
import com.example.kotlin.R
import com.example.kotlin.view.adapter.NotesAdapter
import com.example.kotlin.view.base.BaseFragment
import kotlinx.android.synthetic.main.layout_notes_fragment.*


class NotesFragment : BaseFragment() {
    companion object {
        fun newInstance() = EditorFragment()
    }

    private lateinit var viewModel: EditroViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setToolbar(toolbar)

        viewModel = ViewModelProviders.of(this).get(EditroViewModel::class.java)
        adapter = NotesAdapter()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer<EditorViewState> { t ->
            t?.let { adapter.notes = it.notes }
        })
    }
}
