package com.maxdexter.myrecipe.repository

import com.maxdexter.myrecipe.database.NoteDao
import com.maxdexter.myrecipe.model.Note
import kotlinx.coroutines.*

class NoteRepository(private val noteDao: NoteDao) {

//    var note = Note(title = "My simple note", description = "My simple note text")
//    private var notes: MutableList<Note> =
//        mutableListOf(note, note, note, note, note, note, note, note, note, note)


    val notes = noteDao.getAllNote()

    suspend fun insert(note: Note)  {
        withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }

    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: NoteRepository? = null

        fun getInstance(noteDao: NoteDao) =
            instance ?: synchronized(this) {
                instance ?: NoteRepository(noteDao).also { instance = it }
            }
    }


}