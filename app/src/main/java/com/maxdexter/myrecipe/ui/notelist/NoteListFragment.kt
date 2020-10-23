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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.NoteAdapter
import com.maxdexter.myrecipe.adapter.NoteListener
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.ui.event.EventType
import org.koin.android.ext.android.get


class NoteListFragment : Fragment() {
    private lateinit var viewModel: NoteListViewModel
    private lateinit var viewModelFactory: NoteListViewModelFactory
    private lateinit var binding: FragmentNoteListBinding


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
        binding.executePendingBindings()

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
                null -> Log.i("EVENT", "null event")
            }
        })
    }



    private fun initRecycler() {
        val adapter = NoteAdapter(NoteListener { recipe -> this.findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToViewPagerRecipeFragment2(recipe)) })
        viewModel.notes?.observe(viewLifecycleOwner, {
            adapter.submitList(it)   })
        val recyclerView = binding.recycler
        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.endList.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(context, "udate", Toast.LENGTH_SHORT).show()
                viewModel.updateList()
            }
        })
    }




}