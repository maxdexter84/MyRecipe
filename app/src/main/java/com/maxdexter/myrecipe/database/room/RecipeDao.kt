package com.maxdexter.myrecipe.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maxdexter.myrecipe.model.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe):Long

    @Update
    suspend fun update(recipe:Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipe where id =:id")
    fun getRecipeById(id: Long):LiveData<Recipe>

    @Query("SELECT * FROM recipe")
    fun getAllRecipe(): LiveData<List<Recipe>>
}