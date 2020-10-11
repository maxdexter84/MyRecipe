package com.maxdexter.myrecipe.database.firestore

import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.model.User
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    suspend fun subscribeToAllNotes(): ReceiveChannel<MutableList<Recipe>>
    suspend fun getNoteById(uuid: String): Recipe
    suspend fun saveNote(recipe: Recipe) : Recipe
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(recipe:Recipe): Boolean
}