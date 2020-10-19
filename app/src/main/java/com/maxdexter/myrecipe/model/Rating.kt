package com.maxdexter.myrecipe.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating( var rating: Double = 0.0,
                   val sumOfScore: Double = 0.0,
                   val numberOfRatings: Double = 0.0,
                   val user: User? = null
) : Parcelable