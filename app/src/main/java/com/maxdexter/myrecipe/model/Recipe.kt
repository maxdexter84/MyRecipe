package com.maxdexter.myrecipe.model

import android.media.Image

data class Recipe(val nameOfDish: String, val image: MutableList<Image>,val ingredients: MutableMap<String, String>)