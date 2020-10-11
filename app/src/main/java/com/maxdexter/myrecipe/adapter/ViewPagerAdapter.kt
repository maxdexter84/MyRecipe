package com.maxdexter.myrecipe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maxdexter.myrecipe.ui.recipe_detail.recipepage.recipeinfopage.RecipeInfoFragment
import com.maxdexter.myrecipe.ui.recipe_detail.recipepage.сookinginstructionspage.CookingInstructionsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
      return  when(position) {
            0 -> RecipeInfoFragment.newInstance()
            else -> CookingInstructionsFragment.newInstance()
      }
    }

}