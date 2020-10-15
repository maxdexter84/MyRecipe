package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.RecipeDao
import com.maxdexter.myrecipe.model.Recipe
import kotlinx.coroutines.*

class NoteRepository(private val database: AppDatabase, private val remoteProvider: RemoteDataProvider) {
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)
    private val mutableList = mutableListOf<Recipe>()
    private val recipeDao: RecipeDao = database.recipeDao()
    private val _notes = MutableLiveData<MutableList<Recipe>>()
            val notes: LiveData<MutableList<Recipe>>
            get() = _notes

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
    getFireRecipe()

}

    //val notes = recipeDao.getAllRecipe()
     fun getFireRecipe(){
       coroutineScope.launch {
          mutableList.addAll(remoteProvider.subscribeToAllNotes().receive())
           _notes.value =  mutableList
       }
    }

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