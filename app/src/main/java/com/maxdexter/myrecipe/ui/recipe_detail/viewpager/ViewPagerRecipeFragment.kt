package com.maxdexter.myrecipe.ui.recipe_detail.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R

class ViewPagerRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = ViewPagerRecipeFragment()
    }

    private lateinit var viewModelViewPager: ViewPagerRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_pager_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelViewPager = ViewModelProvider(this).get(ViewPagerRecipeViewModel::class.java)

    }


}