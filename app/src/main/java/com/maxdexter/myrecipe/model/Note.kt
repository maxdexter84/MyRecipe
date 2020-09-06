package com.maxdexter.myrecipe.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*



@Entity(tableName = "table_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String = UUID.randomUUID().toString(),
    var title: String? = "",
    var description: String? = "",
    var noteColor: Int? = 0

)