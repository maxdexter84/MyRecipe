package com.maxdexter.myrecipe.adapter

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.maxdexter.myrecipe.model.Note

@BindingAdapter("textTitle")
fun TextView.setTitle(note: Note) {
    text = note.mTitle
}

@BindingAdapter("textDescription")
fun TextView.setDescription(note: Note) {
    text = note.mDescription
}

@BindingAdapter("cardViewColor")
fun CardView.setBackgroundColor(note: Note) {
    setBackgroundColor(resources.getColor(note.noteColor))
}

@BindingAdapter("dateTime")
fun TextView.setTime(note: Note){
    text = note.mDate
}

