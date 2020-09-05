package com.maxdexter.myrecipe.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.database.NoteDao
import com.maxdexter.myrecipe.repository.NoteRepository

class DetailViewModelFactory(val noteId: Int, val repository: NoteRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(noteId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}