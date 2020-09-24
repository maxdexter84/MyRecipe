package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.model.Note
import kotlinx.coroutines.*

class NoteRepository(private val database: AppDatabase, private val remoteProvider: RemoteDataProvider) {
    private val noteDao: NoteDao = database.noteDao()


    suspend fun  synchronization() = remoteProvider.subscribeToAllNotes()
    suspend fun saveNoteInFireStore(note: Note) = remoteProvider.saveNote(note)
    suspend fun getNoteByIdFromFireStore(uuid: String) = remoteProvider.getNoteById(uuid)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
    suspend fun loadToFireStore(allNotes: List<Note>) {
        allNotes.forEach { note -> saveNoteInFireStore(note) }
    }
    suspend fun deleteNoteFromFireStore(note: Note) {
       withContext(Dispatchers.IO) {
           remoteProvider.deleteNote(note)
       }
    }

init {

}

    val notes = noteDao.getAllNote()

    suspend fun insert(note: Note): Long {
        var id: Long
        withContext(Dispatchers.IO) {
           id = noteDao.insert(note)
        }
        return id
    }

  fun getNote(id: Long): LiveData<Note> {
      return noteDao.getNoteFromId(id)
  }

   suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }




}