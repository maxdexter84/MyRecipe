package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.database.firestore.FireStoreProvider
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.model.Note
import kotlinx.coroutines.*

class NoteRepository(private val noteDao: NoteDao) {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun synchronization() = remoteProvider.subscribeToAllNotes()
    fun saveNoteInFireStore(note: Note) = remoteProvider.saveNote(note)
    fun getNoteByIdFromFireStore(uuid: String) = remoteProvider.getNoteById(uuid)

    fun loadToFireStore(allNotes: List<Note>) {
        allNotes.forEach { note -> saveNoteInFireStore(note) }
    }



    val notes = noteDao.getAllNote()

    suspend fun insert(note: Note)  {
        withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

  fun getNote(id: Int): LiveData<Note> {
      return noteDao.getNoteFromId(id)
  }

   suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    companion object {
        @Volatile private var instance: NoteRepository? = null
        fun getInstance(noteDao: NoteDao) =
            instance ?: synchronized(this) {
                instance ?: NoteRepository(noteDao).also { instance = it }
            }
    }


}