package com.maxdexter.myrecipe.View.notelist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.repository.NoteRepository

class NoteListViewModelFactory(private val noteRepository: NoteRepository, private val lifecycleOwner: LifecycleOwner): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteListViewModel(noteRepository, lifecycleOwner) as T
    }
}
