package com.maxdexter.myrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxdexter.myrecipe.databinding.ListItemNoteBinding
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.ui.notelist.NoteListViewModel

class NoteAdapter (val clickListener: NoteListener, val viewModel: NoteListViewModel): ListAdapter<Recipe,NoteViewHolder>(NoteAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener,item, viewModel)

    }
}


class NoteViewHolder private constructor(private val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: NoteListener, recipe: Recipe, viewModel: NoteListViewModel) = with (recipe){
        binding.noteItem = recipe
        binding.clickListener = clickListener
        binding.listViewModel = viewModel
        binding.executePendingBindings()
    }


    companion object {
        fun from (parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemNoteBinding.inflate(layoutInflater,parent, false)
            return NoteViewHolder(binding)
        }
    }

}

/**
 * Обратный вызов для вычисления разницы между двумя ненулевыми элементами в списке.
 *
 * Используется ListAdapter для расчета минимального количества изменений между старым списком и новым
 * список, который был передан в " submitList`.
 */
class NoteAdapterDiffCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

}

class NoteListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(recipe: Recipe) = clickListener(recipe.id)
}
