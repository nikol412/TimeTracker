package com.example.timetracker.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.data.db.model.Task
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.data.db.repository.UserRepository
import com.example.timetracker.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    var homeLabel = MutableLiveData<String>()
    var tasks = MutableLiveData<List<Task>>()
    var todayTasks = MutableLiveData<List<Task>>()

    init {
        App.appComponent?.inject(this)

        taskRepository.getTasksByDate()?.addChangeListener { result, changeSet ->

            val today = result.filter { checkIsTaskToday(it.createdAt) }.toMutableList()
            val other = result.filter { !checkIsTaskToday(it.createdAt) }.toMutableList()

            todayTasks.value = today
            tasks.value = other

//            tasks.value = result.toList()
        }
    }

    private fun checkIsTaskToday(taskDate: Date?): Boolean {
        if (taskDate == null) return false

        val todayDate = Calendar.getInstance().time

        val sdf = SimpleDateFormat("yyyyMMdd")
        val taskString = sdf.format(taskDate)
        val todayString = sdf.format(todayDate)
        return sdf.format(taskDate) == sdf.format(todayDate)
    }
}