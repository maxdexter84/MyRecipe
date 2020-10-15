package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.*
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.model.User
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
    private var currentRecipe: Recipe? = null

    private val _eventType = MutableLiveData<EventType>()
            val eventType: LiveData<EventType>
            get() = _eventType



    init {
        _eventType.value = EventType.NO_EVENTS
        uiScope.launch {val user: User? = repository?.getCurrentUser()
            if (user != null){currentUser = true}
        }
        //repository?.getCurrentUser()?.observe(lifecycleOwner, Observer { if (it != null) currentUser = true })
    }


    fun deleteNote() = viewModelScope.launch {
        currentRecipe?.let { repository?.deleteNote(it) }

    }

    fun deleteNoteFromFireStore() = uiScope.launch {
        currentRecipe?.let { repository?.deleteNoteFromFireStore(it) }
    }

    fun updateList() {
        repository?.getFireRecipe()
    }



    fun deleteItem(recipe: Recipe) {
        currentRecipe = recipe
        if (currentUser){
            _eventType.value = EventType.DELETE_NOTE_AUTH
        }else {
            _eventType.value = EventType.DELETE_NOTE_NOT_AUTH
            }
    }

    public override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }

}