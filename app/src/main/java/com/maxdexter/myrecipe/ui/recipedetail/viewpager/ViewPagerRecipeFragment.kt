package com.maxdexter.myrecipe.ui.recipedetail.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.ViewPagerAdapter
import com.maxdexter.myrecipe.databinding.ViewPagerRecipeFragmentBinding

class ViewPagerRecipeFragment : Fragment() {



    private lateinit var viewModelViewPager: ViewPagerRecipeViewModel
    private lateinit var binding: ViewPagerRecipeFragmentBinding
    private lateinit var pagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.view_pager_recipe_fragment, container, false)
        val args = arguments?.let { ViewPagerRecipeFragmentArgs.fromBundle(it) }
        if (args != null) {
            pagerAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle,args.recipe)
        }

        binding.viewPager2.adapter = pagerAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelViewPager = ViewModelProvider(this).get(ViewPagerRecipeViewModel::class.java)

    }


}