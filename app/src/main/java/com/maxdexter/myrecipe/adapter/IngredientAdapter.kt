package com.maxdexter.myrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.ListItemIngredientBinding
import com.maxdexter.myrecipe.model.Ingredient

class IngredientAdapter : ListAdapter<Ingredient, IngredientAdapter.ViewHolder>(IngredientAdapterItemCallback()){


    class ViewHolder(val binding: ListItemIngredientBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient){
            binding.item = ingredient
            binding.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientAdapter.ViewHolder {
        val binding = ListItemIngredientBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class IngredientAdapterItemCallback : DiffUtil.ItemCallback<Ingredient>() {
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
     return  oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
      return  oldItem == newItem
    }

}