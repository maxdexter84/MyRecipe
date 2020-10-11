package com.maxdexter.myrecipe.ui.recipe_detail.recipepage.сookinginstructionspage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxdexter.myrecipe.R

class CookingInstructionsFragment : Fragment() {

    companion object {
        fun newInstance() = CookingInstructionsFragment()
    }

    private lateinit var viewModel: CookingInstructionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cooking_instructions_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CookingInstructionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}