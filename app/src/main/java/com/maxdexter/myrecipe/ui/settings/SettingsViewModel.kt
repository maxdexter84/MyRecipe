package com.maxdexter.myrecipe.ui.settings




import androidx.lifecycle.*


import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.User

import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SettingsViewModel(private val repository: NoteRepository?,private val owner: LifecycleOwner) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var notes = repository?.notes

    private var _isAuth = MutableLiveData<Boolean>()
            val isAuth: LiveData<Boolean>
            get() = _isAuth

    private var _logOut = MutableLiveData<Boolean>()
            val logOut: LiveData<Boolean>
            get() = _logOut


    init {
        _logOut.value = false
        isAuthFunc()
    }

    private fun isAuthFunc() {
        uiScope.launch {
            repository?.getCurrentUser().let {_isAuth.value = it != null
                if (it != null) {
                    onLoadToFireStore()
                }
            }

        }


    }

    fun logOut(){
        _logOut.value = true
    }

    private fun onLoadToFireStore() {
        notes?.observe(owner, Observer{ list ->
            uiScope.launch { repository?.loadToFireStore(list) }
        })
    }


    fun downloadFromFireStore(){
        var listOfNote = mutableListOf<Note>()
        uiScope.launch { repository?.synchronization().let{
            if (it != null) { listOfNote = it.receive()} }
        }
        uiScope.launch {listOfNote.toSet().forEach{note ->  repository?.insert(note) }  }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}