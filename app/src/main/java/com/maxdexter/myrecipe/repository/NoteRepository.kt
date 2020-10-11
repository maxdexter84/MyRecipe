package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.model.Recipe
import kotlinx.coroutines.*

class NoteRepository(private val database: AppDatabase, private val remoteProvider: RemoteDataProvider) {
    private val noteDao: NoteDao = database.noteDao()


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

}

    val notes = noteDao.getAllNote()

    suspend fun insert(recipe: Recipe): Long {
        var id: Long
        withContext(Dispatchers.IO) {
           id = noteDao.insert(recipe)
        }
        return id
    }

  fun getNote(id: Long): LiveData<Recipe> {
      return noteDao.getNoteFromId(id)
  }

   suspend fun updateNote(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            noteDao.update(recipe)
        }
    }

    suspend fun deleteNote(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            noteDao.delete(recipe)
        }
    }




}