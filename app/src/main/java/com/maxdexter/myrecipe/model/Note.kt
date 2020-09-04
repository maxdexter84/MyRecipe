package com.maxdexter.myrecipe.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = "",
    val description: String? = "" ,
    val noteColor: Int? = 0

)