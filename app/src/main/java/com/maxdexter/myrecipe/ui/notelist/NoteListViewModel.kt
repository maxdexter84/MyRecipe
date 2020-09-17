package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.ui.event.EventType
import kotlinx.coroutines.*

class NoteListViewModel(
    val repository: NoteRepository?,
    val binding: FragmentNoteListBinding, val lifecycleOwner: LifecycleOwner) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = repository?.notes
    private var currentUser: Boolean = false
    private var currentNote: Note? = null

    private val _navigate = MutableLiveData<Boolean>()
            val navigate: LiveData<Boolean>
            get() = _navigate

    private val _eventType = MutableLiveData<EventType>()
            val eventType: LiveData<EventType>
            get() = _eventType



    init {
        _navigate.value = false
        _eventType.value = EventType.NO_EVENTS
        repository?.getCurrentUser()?.observe(lifecycleOwner, Observer { if (it != null) currentUser = true })
    }


    fun actionWithANote(note: Note) {
        deleteItem(note)
    }
    fun deleteNote() = uiScope.launch {
        currentNote?.let { repository?.deleteNote(it) }

    }

    fun deleteNoteFromFireStore() = uiScope.launch {
        currentNote?.let { repository?.deleteNoteFromFireStore(it) }
    }

    private fun navigateToDetailFragment(addNote: Boolean) {
        _navigate.value = addNote
    }

    private fun deleteItem(note: Note) {
        currentNote = note
        if (currentUser){
            _eventType.value = EventType.DELETE_NOTE_AUTH
        }else {
            _eventType.value = EventType.DELETE_NOTE_NOT_AUTH
            }
    }

    override fun onCleared() {
        super.onCleared()
        navigateToDetailFragment(false)
        viewModelJob.cancel()

    }

}