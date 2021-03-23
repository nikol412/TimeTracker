package com.example.timetracker.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.data.db.model.Task
import com.example.timetracker.data.db.model.User
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.data.db.repository.UserRepository
import com.example.timetracker.ui.base.BaseViewModel
import io.realm.RealmResults
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    var homeLabel = MutableLiveData<String>()
    var tasks = MutableLiveData<List<Task>>()

    init {
        App.appComponent?.inject(this)

        taskRepository.getTasksByDate()?.addChangeListener { result, changeSet ->
            tasks.value = result.toList()
        }
    }
}