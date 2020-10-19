package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxdexter.myrecipe.model.Ingredient

class NewRecipeViewModel : ViewModel() {
    private val list = MutableList<Ingredient?>(1) { null }
    private lateinit var ingredient: Ingredient
    private val _ingredientsList = MutableLiveData<List<Ingredient?>>()
            val ingredientList: LiveData<List<Ingredient?>>
            get() = _ingredientsList

    init {
        _ingredientsList.value = list
    }
     fun addIngredient(title: String, quantity: Double, measure: String) {
         ingredient = Ingredient(title, quantity, measure)
         list.add(ingredient)
         _ingredientsList.value = list
     }

    fun deleteIngredient() {
        list.remove(ingredient)
        _ingredientsList.value = list
    }
}