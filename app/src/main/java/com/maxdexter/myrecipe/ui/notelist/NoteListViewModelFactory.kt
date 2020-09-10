package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.repository.NoteRepository

class NoteListViewModelFactory(private val noteRepository: NoteRepository?): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
