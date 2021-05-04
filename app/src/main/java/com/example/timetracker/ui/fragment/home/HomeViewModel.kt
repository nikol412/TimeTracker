package com.example.timetracker.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.common.extension.toFormattedString
import com.example.timetracker.data.db.model.Task
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.data.db.repository.UserRepository
import com.example.timetracker.data.preferences.TimeTrackerPreferences
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.model.AlertObject
import java.util.*
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    @Inject
    lateinit var preferences: TimeTrackerPreferences

    var homeLabel = MutableLiveData<String>()
    var tasks = MutableLiveData<List<Task>>()


    init {
        App.appComponent?.inject(this)
        fetchTasks()
    }

    private fun fetchTasks() {
        val flowable = taskRepository.getTasksByDate()

        flowable?.let { flowable ->
            compositeDisposable.add(
                flowable.subscribe({ fetchedTasks ->
                    tasks.value = fetchedTasks
                        .filter { !it.isDone }
                        .sortedByDescending { it.date }
                }, { error ->
                    this.onAlertDialogNeeded.value = AlertObject("Error fetching tasks")
                })
            )
        }
    }

    fun markTaskAsDone(position: Int) {
        tasks.value?.getOrNull(position)?.let { task ->
            taskRepository.markAsDone(task, Calendar.getInstance().time.toFormattedString())
        }
    }
}