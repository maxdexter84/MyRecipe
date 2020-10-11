package com.maxdexter.myrecipe.ui.recipe_detail.recipepage.recipeinfopage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxdexter.myrecipe.R

class RecipeInfoFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeInfoFragment()
    }

    private lateinit var viewModel: RecipeInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}