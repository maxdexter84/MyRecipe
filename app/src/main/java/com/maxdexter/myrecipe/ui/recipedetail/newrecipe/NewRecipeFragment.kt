package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.IngredientAdapter
import com.maxdexter.myrecipe.adapter.IngredientAdapterItemCallback
import com.maxdexter.myrecipe.databinding.ListItemIngredientBinding
import com.maxdexter.myrecipe.databinding.NewRecipeFragmentBinding

class NewRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = NewRecipeFragment()
    }
    private lateinit var binding: NewRecipeFragmentBinding

    private lateinit var viewModel: NewRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewRecipeFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(NewRecipeViewModel::class.java)
        val adapter = IngredientAdapter()
        binding.rvIngredients.layoutManager = LinearLayoutManager(context)
        viewModel.ingredientList.observe(viewLifecycleOwner,{it ->
            adapter.submitList(it)
        })
        binding.rvIngredients.adapter = adapter


        binding.btnAddIngredient.setOnClickListener {
            viewModel.addIngredient()
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }



}