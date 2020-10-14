package com.maxdexter.myrecipe.repository

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.RecipeDao
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.model.User
import kotlinx.coroutines.channels.ReceiveChannel

class AppDatabaseFake: AppDatabase() {
    override fun recipeDao(): RecipeDao {
        TODO("Not yet implemented")
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

}

class RemoteDataProviderFake : RemoteDataProvider{
    override suspend fun subscribeToAllNotes(): ReceiveChannel<MutableList<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(uuid: String): Recipe {
        TODO("Not yet implemented")
    }

    override suspend fun saveNote(recipe: Recipe): Recipe {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(recipe: Recipe): Boolean {
        TODO("Not yet implemented")
    }

}