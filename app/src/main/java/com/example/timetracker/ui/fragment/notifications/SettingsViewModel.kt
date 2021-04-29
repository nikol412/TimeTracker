package com.example.timetracker.ui.fragment.notifications

import com.example.timetracker.App
import com.example.timetracker.common.live.SingleLiveEvent
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.data.preferences.TimeTrackerPreferences
import com.example.timetracker.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {

    @Inject
    lateinit var preferences: TimeTrackerPreferences

    @Inject
    lateinit var taskRepository: TaskRepository

    val settingsEvents = SingleLiveEvent<SettingsEvents>()

    init {
        App.appComponent?.inject(this)
    }

    private fun cleanTasks() {
        taskRepository.cleanTasksAsync()
    }

    private fun cleanPreferences() {
        preferences.clearPreferences()
    }

    fun changePin(pin: String) {
        preferences.pin = pin
    }

    fun onCleanTasksClick() {
        cleanTasks()
    }

    fun onCleanAllDataClick() {
        cleanTasks()
        cleanPreferences()
    }

    fun onChangePasswordClick() {
        settingsEvents.value = SettingsEvents.CHANGE_PASSWORD
    }
}

enum class SettingsEvents {
    CHANGE_PASSWORD
}