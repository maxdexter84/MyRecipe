package com.maxdexter.myrecipe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val uuid: UUID,
    val typeNote: String,
    val title: String,
    val description: String,
    val date: String
)