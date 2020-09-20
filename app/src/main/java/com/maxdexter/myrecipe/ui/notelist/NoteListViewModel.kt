package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.NoteListener
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.ui.event.EventType
import kotlinx.coroutines.*

class NoteListViewModel(
    private val repository: NoteRepository?,
    private val lifecycleOwner: LifecycleOwner) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = repository?.notes
    private var currentUser: Boolean = false
    private var currentNote: Note? = null

    private val _eventType = MutableLiveData<EventType>()
            val eventType: LiveData<EventType>
            get() = _eventType



    init {
        _eventType.value = EventType.NO_EVENTS
        repository?.getCurrentUser()?.observe(lifecycleOwner, Observer { if (it != null) currentUser = true })
    }


    fun deleteNote() = viewModelScope.launch {
        currentNote?.let { repository?.deleteNote(it) }

    }

    fun deleteNoteFromFireStore() = uiScope.launch {
        currentNote?.let { repository?.deleteNoteFromFireStore(it) }
    }



    fun deleteItem(note: Note) {
        currentNote = note
        if (currentUser){
            _eventType.value = EventType.DELETE_NOTE_AUTH
        }else {
            _eventType.value = EventType.DELETE_NOTE_NOT_AUTH
            }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }

}