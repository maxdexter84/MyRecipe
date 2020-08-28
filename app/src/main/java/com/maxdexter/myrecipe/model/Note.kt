package com.maxdexter.myrecipe.model

import java.util.*

data class Note(
    val id: Int,
    val uuid: UUID,
    val typeNote: String,
    val title: String,
    val description: String,
    val date: String
)