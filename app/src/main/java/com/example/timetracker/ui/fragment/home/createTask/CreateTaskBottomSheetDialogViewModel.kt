package com.example.timetracker.ui.fragment.home.createTask

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.common.extension.toFormattedString
import com.example.timetracker.common.live.SingleLiveEvent
import com.example.timetracker.data.db.model.Task
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class CreateTaskBottomSheetDialogViewModel : BaseViewModel() {

    @Inject
    lateinit var taskRepository: TaskRepository

    val taskName = MutableLiveData("")
    val taskDescription = MutableLiveData("")
    val taskDate = MutableLiveData("")

    val event = SingleLiveEvent<CreateTaskEvent>()

    init {
        App.appComponent?.inject(this)
    }

    fun onCreateTaskClick() {
        App.appComponent?.inject(this)
        taskRepository.createTask(
            Task(
                taskName.value ?: "",
                taskDate.value ?: "",
                taskDescription.value ?: "",
                Calendar.getInstance().time
            )
        )
        event.value = CreateTaskEvent.ADD_TASK
    }

    fun onDateClick() {
        event.value = CreateTaskEvent.DATE
    }
}

enum class CreateTaskEvent {
    ADD_TASK, DATE
}