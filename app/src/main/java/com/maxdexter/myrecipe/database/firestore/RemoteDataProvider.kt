package com.maxdexter.myrecipe.database.firestore

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<MutableList<Note>>
    fun getNoteById(uuid: String): LiveData<Note>
    fun saveNote(note: Note) : LiveData<Note>
}