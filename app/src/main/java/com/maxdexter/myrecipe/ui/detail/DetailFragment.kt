package com.maxdexter.myrecipe.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.database.AppDatabase
import com.maxdexter.myrecipe.database.NoteDao
import com.maxdexter.myrecipe.databinding.DetailFragmentBinding
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.detail_fragment,container, false)
        getArgs()


        binding.detailViewModel = viewModel
        binding.setLifecycleOwner(this)

        updateNote()
        return binding.root
    }

    private fun getArgs() {
        val database = context?.let { AppDatabase(it) }
        val repository = database?.noteDao()?.let { NoteRepository(it) }
        val args = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        if (repository != null) {
            initViewModel(args, repository)
        }
    }

    private fun initViewModel(args: DetailFragmentArgs?, repository: NoteRepository) {
        if (args != null) {
            viewModelFactory = DetailViewModelFactory(args.id, repository, viewLifecycleOwner)
        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    private fun updateNote() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.addTitle(p0.toString())

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.edDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.addDescription(p0.toString())


            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

}