package com.maxdexter.myrecipe.model


import android.os.Parcelable

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

    val uuid: String = UUID.randomUUID().toString(),

    val recipe: String ="",

    val content: String = "",

    val recipeDescription : String = "",

    val dishType : String = "",

    val cuisine : String = "",

    val date: String = currentDate(),

    val picURL: String = "",

    val authorName: String = "",

    val authorEmail: String = "",
    val nameAuthor: String = "",
    val emailAuthor: String = "",
    var rating: Double = 0.0,
    val sumOfScore: Double = 0.0,
    val numberOfRatings: Double = 0.0
    ) : Parcelable