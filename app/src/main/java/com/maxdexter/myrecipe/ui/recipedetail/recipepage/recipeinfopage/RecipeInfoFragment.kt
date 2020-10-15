package com.maxdexter.myrecipe.ui.recipedetail.recipepage.recipeinfopage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.RecipeInfoFragmentBinding
import com.maxdexter.myrecipe.model.Recipe
private const val RECIPE = "recipe"
class RecipeInfoFragment : Fragment() {

    companion object {
        fun newInstance(recipe: Recipe): RecipeInfoFragment{
            val args = Bundle()
            args.putParcelable("recipe",recipe)
            val fragment = RecipeInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: RecipeInfoViewModel
    private lateinit var binding: RecipeInfoFragmentBinding
    private var recipe: Recipe? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // arguments?.get(RECIPE)?.let { recipe = it }
        recipe = requireArguments().getParcelable(RECIPE)
        binding = RecipeInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        if (recipe != null)
        binding.recipe = recipe
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeInfoViewModel::class.java)

    }

}