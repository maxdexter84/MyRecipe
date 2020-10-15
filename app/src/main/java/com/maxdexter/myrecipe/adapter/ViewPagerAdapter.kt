package com.maxdexter.myrecipe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.ui.recipedetail.recipepage.recipeinfopage.RecipeInfoFragment
import com.maxdexter.myrecipe.ui.recipedetail.recipepage.сookinginstructionspage.CookingInstructionsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,val recipe:Recipe) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
      return  when(position) {
            0 -> RecipeInfoFragment.newInstance(recipe)
            else -> CookingInstructionsFragment.newInstance(recipe)
      }
    }

}