package com.maxdexter.myrecipe.utils

import java.text.SimpleDateFormat
import java.util.*

enum class Color{
    YELLOW,
    WHITE,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}

fun currentDate(): String{
   return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(Date())
}