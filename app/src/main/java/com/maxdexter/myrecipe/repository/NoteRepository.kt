package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.RecipeDao
import com.maxdexter.myrecipe.model.Recipe
import kotlinx.coroutines.*

class NoteRepository(private val database: AppDatabase, private val remoteProvider: RemoteDataProvider) {
    private val recipeDao: RecipeDao = database.recipeDao()


    suspend fun  synchronization() = remoteProvider.subscribeToAllNotes()
    suspend fun saveNoteInFireStore(recipe: Recipe) = remoteProvider.saveNote(recipe)
    suspend fun getNoteByIdFromFireStore(uuid: String) = remoteProvider.getNoteById(uuid)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
    suspend fun loadToFireStore(allRecipes: List<Recipe>) {
        allRecipes.forEach { note -> saveNoteInFireStore(note) }
    }
    suspend fun deleteNoteFromFireStore(recipe: Recipe) {
       withContext(Dispatchers.IO) {
           remoteProvider.deleteNote(recipe)
       }
    }

init {
    val map = mutableMapOf<String, String>()
    val list = mutableListOf<String>()
    val set = mutableSetOf<String>()

}

    val notes = recipeDao.getAllRecipe()

    suspend fun insert(recipe: Recipe): Long {
        var id: Long
        withContext(Dispatchers.IO) {
           id = recipeDao.insert(recipe)
        }
        return id
    }

  fun getNote(id: Long): LiveData<Recipe> {
      return recipeDao.getRecipeById(id)
  }

   suspend fun updateNote(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            recipeDao.update(recipe)
        }
    }

    suspend fun deleteNote(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            recipeDao.delete(recipe)
        }
    }




}