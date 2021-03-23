package com.example.timetracker.ui.fragment.newItem

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.common.live.SingleLiveEvent
import com.example.timetracker.data.db.model.Task
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.data.db.repository.UserRepository
import com.example.timetracker.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class NewItemViewModel: BaseViewModel() {

    @Inject
    lateinit var taskRepository: TaskRepository

    val items = mutableListOf("item 1", "item 2", "item 3")

    var description = MutableLiveData("")
    var title = MutableLiveData("")
    var createdAt = MutableLiveData<Date>()
    var category = MutableLiveData("")

    val onCloseNeeded = SingleLiveEvent<Any>()

    init {
        App.appComponent?.inject(this)
    }

    fun createTask() {
        taskRepository.createTask(
            title = title.value!!,
            category = category.value!!,
            createdAt = Calendar.getInstance().time,
            description = description.value!!)

        onCloseNeeded.call()
    }
}