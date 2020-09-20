package com.maxdexter.myrecipe.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.databinding.DetailFragmentBinding
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.utils.Color
import org.koin.android.ext.android.get


class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModelFactory: DetailViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        getArgs()


        binding.detailViewModel = viewModel
        binding.lifecycleOwner = this


        updateNote()
        navigateToNoteListFragment()
        initSpinner()
        viewModel.indexSpinner.observe(viewLifecycleOwner,{indx -> binding.spinner.setSelection(indx)})

        return binding.root
    }

    private fun navigateToNoteListFragment() {
        viewModel.updateNote.observe(viewLifecycleOwner, { update ->
            if (update) {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToNoteListFragment())
            }
        })
    }

    private fun initSpinner() {
        val color = Color.values()
        val adapter: ArrayAdapter<Color> =
            ArrayAdapter<Color>(requireContext(), android.R.layout.simple_spinner_item, color)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.itemColor(color.get(p2))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun getArgs() {
        val repository: NoteRepository = get()
        val args = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        initViewModel(args, repository)
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