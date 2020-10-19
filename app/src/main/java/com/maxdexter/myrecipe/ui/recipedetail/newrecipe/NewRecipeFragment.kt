package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxdexter.myrecipe.R

class NewRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = NewRecipeFragment()
    }

    private lateinit var viewModel: NewRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_recipe_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewRecipeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}