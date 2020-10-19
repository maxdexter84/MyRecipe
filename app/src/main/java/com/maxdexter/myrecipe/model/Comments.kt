package com.maxdexter.myrecipe.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comments(val user:User, val comment: String) : Parcelable