package com.maxdexter.myrecipe.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxdexter.myrecipe.databinding.ListItemRecipeBinding
import com.maxdexter.myrecipe.model.Recipe


class NoteAdapter (val clickListener: NoteListener): ListAdapter<Recipe,NoteViewHolder>(NoteAdapterDiffCallback()) {
    private val _endList = MutableLiveData<Boolean>()
            val endList: LiveData<Boolean>
            get() = _endList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener,item, position)
        if (position == (itemCount - 1)) {
            Log.i("SCROLL", "$position")
            _endList.value = true
        }
    }
}


class NoteViewHolder private constructor(private val binding: ListItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: NoteListener, recipe: Recipe, position: Int) = with (recipe){
        binding.noteItem = recipe
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }


    companion object {
        fun from (parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemRecipeBinding.inflate(layoutInflater,parent, false)
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

class NoteListener(val clickListener: (recipe: Recipe) -> Unit) {
    fun onClick(recipe: Recipe) = clickListener(recipe)
}
