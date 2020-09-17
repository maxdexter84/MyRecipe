package com.maxdexter.myrecipe.repository

import androidx.lifecycle.LiveData
import com.maxdexter.myrecipe.database.firestore.FireStoreProvider
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult
import kotlinx.coroutines.*

class NoteRepository(private val database: AppDatabase) {
    private val noteDao: NoteDao = database.noteDao()
    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun synchronization() = remoteProvider.subscribeToAllNotes()
    fun saveNoteInFireStore(note: Note) = remoteProvider.saveNote(note)
    fun getNoteByIdFromFireStore(uuid: String) = remoteProvider.getNoteById(uuid)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun loadToFireStore(allNotes: List<Note>) {
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

//    companion object {
//        @Volatile private var instance: NoteRepository? = null
//        fun getInstance(noteDao: NoteDao) =
//            instance ?: synchronized(this) {
//                instance ?: NoteRepository(noteDao).also { instance = it }
//            }
//    }


}