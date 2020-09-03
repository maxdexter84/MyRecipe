package com.maxdexter.myrecipe.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maxdexter.myrecipe.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM table_note where id =:id")
    fun getNoteFromId(id: Int):LiveData<Note>

    @Query("SELECT * FROM table_note")
    fun getAllNote(): LiveData<List<Note>>
}