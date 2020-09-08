package com.maxdexter.myrecipe.database.firestore

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(uuid: String): LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
}