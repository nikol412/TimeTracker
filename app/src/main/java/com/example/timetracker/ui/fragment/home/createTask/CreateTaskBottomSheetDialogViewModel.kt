package com.example.timetracker.ui.fragment.home.createTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.App
import com.example.timetracker.common.live.SingleLiveEvent
import com.example.timetracker.ui.base.BaseViewModel

class CreateTaskBottomSheetDialogViewModel: BaseViewModel() {

    val taskName = MutableLiveData("")
    val taskDescription = MutableLiveData("")
    val taskDate = MutableLiveData("")

    val event = SingleLiveEvent<CreateTaskEvent>()

    init {
        App.appComponent?.inject(this)
    }

    fun onCreateTaskClick() {
        event.value = CreateTaskEvent.ADD_TASK
    }

    fun onDateClick() {
        event.value = CreateTaskEvent.DATE
    }
}

enum class CreateTaskEvent {
    ADD_TASK, DATE
}