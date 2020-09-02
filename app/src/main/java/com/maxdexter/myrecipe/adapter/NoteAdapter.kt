package com.maxdexter.myrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.model.Note

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

class NoteViewHolder private constructor(item: View) : RecyclerView.ViewHolder(item) {
    private val imageView: AppCompatImageView = item.findViewById(R.id.iv_note_image)
    private val textView: TextView = item.findViewById(R.id.tv_title)

    fun bind(note: Note) {
        textView.text = note.title
    }


    companion object {
        fun from (parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_note,parent, false )
            return NoteViewHolder(view)
        }
    }

}
