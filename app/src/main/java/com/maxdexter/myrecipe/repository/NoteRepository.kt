package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.database.AppDatabase
import com.maxdexter.myrecipe.database.NoteDao
import com.maxdexter.myrecipe.model.Note
import kotlinx.coroutines.*

class NoteRepository(private val noteDao: NoteDao) {
    val notes = noteDao.getAllNote()

    suspend fun insert(note: Note)  {
        withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

  fun getNote(id: Int): LiveData<Note> {
      return noteDao.getNoteFromId(id)
  }

    companion object {
        @Volatile private var instance: NoteRepository? = null
        fun getInstance(noteDao: NoteDao) =
            instance ?: synchronized(this) {
                instance ?: NoteRepository(noteDao).also { instance = it }
            }
    }


}