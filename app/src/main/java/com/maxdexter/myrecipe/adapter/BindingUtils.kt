package com.maxdexter.myrecipe.adapter

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.maxdexter.myrecipe.model.Recipe

@BindingAdapter("textTitle")
fun TextView.setTitle(recipe: Recipe) {
    text = recipe.mTitle
}

@BindingAdapter("textDescription")
fun TextView.setDescription(recipe: Recipe) {
    text = recipe.mDescription
}

@BindingAdapter("cardViewColor")
fun CardView.setBackgroundColor(recipe: Recipe) {
    setBackgroundColor(resources.getColor(recipe.noteColor))
}

@BindingAdapter("dateTime")
fun TextView.setTime(recipe: Recipe){
    text = recipe.mDate
}

