package com.maxdexter.myrecipe.adapter

import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.utils.Color

@BindingAdapter("textTitle")
fun TextView.setTitle(note: Note) {
    text = note.title
}

@BindingAdapter("textDescription")
fun TextView.setDescription(note: Note) {
    text = note.description
}

@BindingAdapter("cardViewColor")
fun CardView.setBackgroundColor(note: Note) {
    setBackgroundColor(resources.getColor(note.noteColor))
}

