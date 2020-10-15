package com.maxdexter.myrecipe.ui.recipedetail.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
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
        val viewPager = binding.viewPager2
        viewPager.adapter  = pagerAdapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0 -> tab.text = "Ингредиенты"
                1 -> tab.text = "Инструкция"
            }
        }.attach()


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelViewPager = ViewModelProvider(this).get(ViewPagerRecipeViewModel::class.java)

    }


}