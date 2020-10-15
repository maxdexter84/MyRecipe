package com.maxdexter.myrecipe.ui.recipedetail.recipepage.сookinginstructionspage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.bindImage
import com.maxdexter.myrecipe.databinding.CookingInstructionsFragmentBinding
import com.maxdexter.myrecipe.databinding.RecipeInfoFragmentBinding
import com.maxdexter.myrecipe.model.Recipe

const val RECIPE = "recipe"
class CookingInstructionsFragment : Fragment() {

    companion object {
        fun newInstance(recipe: Recipe): CookingInstructionsFragment {
            val args = Bundle()
            args.putParcelable(RECIPE,recipe)
            val fragment = CookingInstructionsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: CookingInstructionsViewModel
    private lateinit var binding: CookingInstructionsFragmentBinding
    private var recipe: Recipe? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CookingInstructionsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        recipe = requireArguments().getParcelable(RECIPE)
        binding.recipe = recipe
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CookingInstructionsViewModel::class.java)
    }

}