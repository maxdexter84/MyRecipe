package com.maxdexter.myrecipe.adapter

import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.maxdexter.myrecipe.model.Note

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
    note.noteColor?.let { setBackgroundColor(it) }
}