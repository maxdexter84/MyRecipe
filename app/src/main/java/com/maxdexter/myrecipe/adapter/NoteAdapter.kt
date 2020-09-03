package com.maxdexter.myrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.model.Note
import kotlinx.android.synthetic.main.list_item_note.view.*

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return data.size
    }

}

class NoteAdapterDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}

class NoteViewHolder private constructor(item: View) : RecyclerView.ViewHolder(item) {
    private val textTitle = itemView.tv_title
    private val textDescription = itemView.tv_description

    fun bind(note: Note) = with (note){
        textTitle.text = title
        textDescription.text = description
        itemView.setBackgroundColor(noteColor)
    }


    companion object {
        fun from (parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_note,parent, false )
            return NoteViewHolder(view)
        }
    }

}
