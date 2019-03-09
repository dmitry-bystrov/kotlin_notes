package com.example.kotlin.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.kotlin.R
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardView = itemView.findViewById<RelativeLayout>(R.id.rl_card)
    private val titleView = itemView.findViewById<TextView>(R.id.tv_title)
    private val contentView = itemView.findViewById<TextView>(R.id.tv_content)

    fun bind(note: Note) {
        with(note) {
            val background = when (note.color) {
                NoteColor.ORANGE -> R.drawable.shape_note_orange
                NoteColor.BROWN -> R.drawable.shape_note_brown
                NoteColor.GREEN -> R.drawable.shape_note_green
                NoteColor.CYAN -> R.drawable.shape_note_cyan
                NoteColor.PURPLE -> R.drawable.shape_note_purple
                NoteColor.PINK -> R.drawable.shape_note_pink
                NoteColor.LIME -> R.drawable.shape_note_lime
                NoteColor.RED -> R.drawable.shape_note_red
            }

            titleView.text = title
            contentView.text = content
            cardView.background = itemView.resources.getDrawable(background, null)
        }
    }
}