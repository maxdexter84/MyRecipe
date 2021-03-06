package com.maxdexter.myrecipe.ui.notelist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.NoteAdapter
import com.maxdexter.myrecipe.adapter.NoteListener
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.ui.event.EventType
import org.koin.android.ext.android.get


class NoteListFragment : Fragment() {
    private lateinit var viewModel: NoteListViewModel
    private lateinit var viewModelFactory: NoteListViewModelFactory
    private lateinit var binding: FragmentNoteListBinding
    private  var noteDao: NoteDao? = null
   // private var currentUser: Boolean = false
    companion object {
        fun newInstance() = NoteListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)

        val noteRepository: NoteRepository = get()
        viewModelFactory = NoteListViewModelFactory(noteRepository,viewLifecycleOwner)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NoteListViewModel::class.java)
        binding.noteListViewModel = viewModel
        binding.lifecycleOwner = this

        eventTypeObserver()
        initRecycler()
        return binding.root
    }

    private fun eventTypeObserver() {
        viewModel.eventType.observe(viewLifecycleOwner, {
            when (it) {
                EventType.DELETE_NOTE_NOT_AUTH -> Snackbar.make(
                    binding.root,
                    R.string.toast_login_about_delete,
                    Snackbar.LENGTH_SHORT
                ).show()
                EventType.DELETE_NOTE_AUTH -> Snackbar.make(
                    binding.root,
                    R.string.snackbar_delete_note_title,
                    Snackbar.LENGTH_LONG
                ).setAction("Yes") {
                    viewModel.deleteNoteFromFireStore()
                    viewModel.deleteNote()
                }.show()
                EventType.NAVIGATE -> Log.i("EVENT", "Event type NAVIGATE")
                EventType.NO_EVENTS -> Log.i("EVENT", "Event type NO_EVENT")
            }
        })
    }



    private fun initRecycler() {
        val adapter = NoteAdapter(NoteListener { id -> this.findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToDetailFragment(id)) }, viewModel)
        viewModel.notes?.observe(viewLifecycleOwner, { adapter.submitList(it)   })
        val recyclerView = binding.recycler
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }




}