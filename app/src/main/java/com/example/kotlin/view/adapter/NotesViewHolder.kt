package com.example.kotlin.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.kotlin.R
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.NoteColor

class NotesViewHolder(
    itemView: View,
    private val clickListener: OnItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val colorLine = itemView.findViewById<View>(R.id.v_color_line)
    private val titleView = itemView.findViewById<TextView>(R.id.tv_title)
    private val contentView = itemView.findViewById<TextView>(R.id.tv_content)

    fun bind(note: Note) {
        with(note) {
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

            titleView.text = title
            contentView.text = content
            colorLine.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            itemView.setOnClickListener { clickListener.onItemClick(note) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }
}