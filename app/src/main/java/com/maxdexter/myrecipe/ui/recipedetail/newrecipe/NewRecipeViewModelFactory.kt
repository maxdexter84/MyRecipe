package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewRecipeViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewRecipeViewModel(context) as T
    }
}