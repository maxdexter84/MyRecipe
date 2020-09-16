package com.maxdexter.myrecipe.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maxdexter.myrecipe.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note):Long

    @Update
    suspend fun update(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note where id =:id")
    fun getNoteFromId(id: Long):LiveData<Note>

    @Query("SELECT * FROM note")
    fun getAllNote(): LiveData<List<Note>>
}