package com.example.timetracker.ui.fragment.newItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentNewItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewItemBottomSheetDialog: BottomSheetDialogFragment() {

    val viewModel: NewItemViewModel by viewModels()
    lateinit var binding: FragmentNewItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_item, container, false)
        //val root = inflater.inflate(R.layout.fragment_new_item, container, false)
        configureDropDownMenu()
        return binding.root
    }


    private fun configureDropDownMenu() {
        val adapter = ArrayAdapter(requireContext(), R.layout.category_drop_down_item_row, viewModel.items)
        binding.filledExposedDropdown.setAdapter(adapter)
    }

    fun subscribeToViewModel() {
        viewModel.onCloseNeeded.observe(viewLifecycleOwner, Observer {
            this.dismiss()
        })
    }

}