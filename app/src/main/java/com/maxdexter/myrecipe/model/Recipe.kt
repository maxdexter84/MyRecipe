package com.maxdexter.myrecipe.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxdexter.myrecipe.utils.currentDate
import kotlinx.android.parcel.Parcelize

import java.util.*


@Parcelize
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "uuid")
    val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title")
    val recipe : String ="",
    @ColumnInfo(name = "ingredients")
    val content : String = "",
    @ColumnInfo(name = "description")
    val recipeDescription : String = "",
    @ColumnInfo(name = "dishType")
    val dishType : String = "",
    @ColumnInfo(name = "cuisine")
    val cuisine : String = "",
    @Embedded
    val comments: Comments,
    @ColumnInfo(name ="date")
    val date: String = currentDate(),
    @ColumnInfo(name = "picURL")
    val picURL: String = "",
    @Embedded
    val rating: Rating,
    @Embedded
    var author: User

) : Parcelable