package com.maxdexter.myrecipe.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxdexter.myrecipe.utils.currentDate

import java.util.*



@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "mUUID")
    val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title")
    var mTitle: String = "",
    @ColumnInfo(name = "description")
    var mDescription: String = "",
    @ColumnInfo(name = "type_note")
    var noteColor: Int = 0,
    @ColumnInfo(name = "date")
    var mDate: String = currentDate()

)