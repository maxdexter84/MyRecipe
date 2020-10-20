package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxdexter.myrecipe.model.Ingredient
import java.util.*

class NewRecipeViewModel : ViewModel() {
    private var ingredient: Ingredient = Ingredient("", "", "",uuid = UUID.randomUUID().toString())
    private val list = mutableListOf(ingredient)

    private val _ingredientsList = MutableLiveData<List<Ingredient>>()
            val ingredientList: LiveData<List<Ingredient>>
            get() = _ingredientsList

    init {
        _ingredientsList.value = list
    }
     fun addIngredient() {
         ingredient = Ingredient("", "", "",uuid = UUID.randomUUID().toString())
         list.add(ingredient)
         _ingredientsList.value = list
     }

    fun deleteIngredient() {
        list.remove(ingredient)
        _ingredientsList.value = list
    }
}