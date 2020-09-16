package com.maxdexter.myrecipe.ui.notelist

import android.os.Bundle
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


class NoteListFragment : Fragment() {
    private lateinit var viewModel: NoteListViewModel
    private lateinit var viewModelFactory: NoteListViewModelFactory
    private lateinit var binding: FragmentNoteListBinding

    companion object {
        fun newInstance() = NoteListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)
        val noteDao = context?.let { AppDatabase(it) }?.noteDao()
        viewModelFactory = NoteListViewModelFactory(noteDao?.let { NoteRepository(it) })
        viewModel = ViewModelProvider(this,viewModelFactory).get(NoteListViewModel::class.java)
        binding.noteListViewModel = viewModel
        binding.lifecycleOwner = this


        initRecycler()
        navigate()
        deleteItem()
        return binding.root
    }

    private fun deleteItem() {
        viewModel.action.observe(viewLifecycleOwner, { note ->
            if (note != null) {
                Snackbar.make(binding.root, "Are You sure?", Snackbar.LENGTH_LONG)
                    .setAction("Yes") {
                        note.let { it1 ->
                            viewModel.deleteNote(it1)
                        }
                    }.show()
            }
        })
    }

    private fun navigate() {
        viewModel.navigate.observe(viewLifecycleOwner, { add ->
            if (add) {
                findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragmentToDetailFragment(
                        -1
                    )
                )
            }
        })
    }

    private fun initRecycler() {
        val adapter = NoteAdapter(NoteListener { uuid -> this.findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToDetailFragment(uuid)) }, viewModel)
        viewModel.notes?.observe(viewLifecycleOwner, { adapter.submitList(it)   })
        val recyclerView = binding.recycler
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }




}