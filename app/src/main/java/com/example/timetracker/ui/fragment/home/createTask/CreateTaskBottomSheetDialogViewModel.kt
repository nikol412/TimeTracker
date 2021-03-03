package com.example.timetracker.ui.fragment.home.createTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.App
import com.example.timetracker.ui.base.BaseViewModel

class CreateTaskBottomSheetDialogViewModel: BaseViewModel() {

    val taskName = MutableLiveData("")
    val taskDescription = MutableLiveData("")
    val taskDate = MutableLiveData("")

    init {
        App.appComponent?.inject(this)
    }
}