package com.maxdexter.myrecipe.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.FragmentBottomsheetBinding
import com.maxdexter.myrecipe.repository.NoteRepository
import org.koin.android.ext.android.get

class BottomsheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bottomsheet,container,false)
        return binding.root
    }


}