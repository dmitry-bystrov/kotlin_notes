package com.example.kotlin.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlin.R
import com.example.kotlin.model.entity.Note

class NotesAdapter(private val clickListener: NotesViewHolder.OnItemClickListener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<NotesViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(view, clickListener)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int) {
        viewHolder.bind(notes[position])
    }
}