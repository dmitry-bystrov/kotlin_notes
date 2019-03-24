package com.example.kotlin.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.kotlin.R
import com.example.kotlin.extensions.getColorInt
import com.example.kotlin.model.entity.Note

class NotesViewHolder(
    itemView: View,
    private val clickListener: OnItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val colorLine = itemView.findViewById<View>(R.id.v_color_line)
    private val titleView = itemView.findViewById<TextView>(R.id.tv_title)
    private val contentView = itemView.findViewById<TextView>(R.id.tv_content)

    fun bind(note: Note) {
        with(note) {
            titleView.text = title
            contentView.text = content
            colorLine.setBackgroundColor(color.getColorInt(itemView.context))
            itemView.setOnClickListener { clickListener.onItemClick(note) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }
}