package com.example.timetracker.ui.fragment.home.createTask

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentCreateTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class CreateTaskBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: CreateTaskBottomSheetDialogViewModel by viewModels()

    fun layoutRes(): Int = R.layout.fragment_create_task

    lateinit var binding: FragmentCreateTaskBinding

    private val datePicker by lazy {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = Calendar.getInstance().timeInMillis

        return@lazy datePicker
    }

    val dateListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.taskDate.value = "$dayOfMonth/$month/$year"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        subscribeViewModelVariables()

        return binding.root
    }

    fun subscribeViewModelVariables() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                CreateTaskEvent.ADD_TASK -> this.dismiss()
                CreateTaskEvent.DATE -> showDatePicker()
            }
        })
    }


    private fun showDatePicker() {
        datePicker.show()
    }


}