package com.maxdexter.myrecipe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*
@Entity(tableName = "table_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val typeNote: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    val imageURL: String? = null

)