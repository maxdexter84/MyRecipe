package com.maxdexter.myrecipe.repository

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.User
import kotlinx.coroutines.channels.ReceiveChannel

class AppDatabaseFake: AppDatabase() {
    override fun noteDao(): NoteDao {
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
    override suspend fun subscribeToAllNotes(): ReceiveChannel<MutableList<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(uuid: String): Note {
        TODO("Not yet implemented")
    }

    override suspend fun saveNote(note: Note): Note {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note): Boolean {
        TODO("Not yet implemented")
    }

}