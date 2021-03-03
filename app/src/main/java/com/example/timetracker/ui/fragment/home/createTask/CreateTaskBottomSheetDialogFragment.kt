package com.example.timetracker.ui.fragment.home.createTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentCreateTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateTaskBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: CreateTaskBottomSheetDialogViewModel by viewModels()

    fun layoutRes(): Int = R.layout.fragment_create_task

    lateinit var binding: FragmentCreateTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.buttonCreateTask.setOnClickListener {

            this.dismiss()
        }

        return binding.root
    }
}