package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.*

class NoteListViewModel(val repository: NoteRepository, lifecycleOwner: LifecycleOwner) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = repository.notes


    fun insert(note: Note) {
        uiScope.launch{repository.insert(note)}
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}