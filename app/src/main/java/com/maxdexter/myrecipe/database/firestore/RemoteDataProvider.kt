package com.maxdexter.myrecipe.database.firestore

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult
import com.maxdexter.myrecipe.model.User

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<MutableList<Note>>
    suspend fun getNoteById(uuid: String): Note
    fun saveNote(note: Note) : LiveData<Note>
    suspend fun getCurrentUser(): User
    suspend fun deleteNote(note:Note): Boolean
}