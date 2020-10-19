package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.IngredientAdapter
import com.maxdexter.myrecipe.databinding.ListItemIngredientBinding

class NewRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = NewRecipeFragment()
    }
    private lateinit var binding: ListItemIngredientBinding

    private lateinit var viewModel: NewRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListItemIngredientBinding.inflate(inflater)

        val adapter = IngredientAdapter()
        viewModel.ingredientList.observe(viewLifecycleOwner,{it ->
            adapter.submitList(it)
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewRecipeViewModel::class.java)

    }

}