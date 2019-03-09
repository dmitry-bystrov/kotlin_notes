package com.example.kotlin.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.kotlin.R
import com.example.kotlin.model.entity.Note

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView = itemView.findViewById<TextView>(R.id.tv_title)
    private val contentView = itemView.findViewById<TextView>(R.id.tv_content)

    fun bind(note: Note) {
        with(note) {
            titleView.text = title
            contentView.text = content
        }
    }
}