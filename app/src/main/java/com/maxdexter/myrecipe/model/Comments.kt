package com.maxdexter.myrecipe.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
data class Comments(val id: Long,val uuid: String, val comment: String)