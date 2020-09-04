package com.maxdexter.myrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.ListItemNoteBinding
import com.maxdexter.myrecipe.model.Note

class NoteAdapter : ListAdapter<Note,NoteViewHolder>(NoteAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

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

class NoteViewHolder private constructor(private val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note) = with (note){
        binding.tvTitle.text = title
        binding.tvDescription.text = description
        if (noteColor != null) {
            itemView.setBackgroundColor(noteColor)
        }
    }


    companion object {
        fun from (parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemNoteBinding.inflate(layoutInflater,parent, false)
            return NoteViewHolder(binding)
        }
    }

}
