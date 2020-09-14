package com.maxdexter.myrecipe.ui.settings



import android.app.AlertDialog
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.maxdexter.myrecipe.model.Note

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
        repository?.getCurrentUser()?.observe(owner, Observer { _isAuth.value = it != null })
    }

    fun logOut(){
        _logOut.value = true
    }

    fun onLoadToFireStore() {
        notes?.observe(owner, Observer{ list -> repository?.loadToFireStore(list) })


    }

    fun downloadFromFireStore(){
        var listOfNote = mutableListOf<Note>()
        repository?.synchronization()?.observe(owner, Observer{it-> listOfNote = it})

        uiScope.launch {listOfNote.forEach{note ->  repository?.insert(note) }  }


    }

    fun logoutDialog(){

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}