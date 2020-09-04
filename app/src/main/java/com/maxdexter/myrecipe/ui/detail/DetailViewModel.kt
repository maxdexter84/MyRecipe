package com.maxdexter.myrecipe.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxdexter.myrecipe.model.Note

class DetailViewModel (noteId: Int): ViewModel() {

   private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note
init {
    checkNoteId(noteId)
}

   private fun checkNoteId(id: Int){
        if(id == -1) {
            newNote()
        }
    }

    private fun newNote() {
       _note.value = Note(title = "new note")
    }




}