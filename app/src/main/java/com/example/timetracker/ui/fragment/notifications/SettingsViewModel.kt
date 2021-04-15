package com.example.timetracker.ui.fragment.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.data.preferences.TimeTrackerPreferences
import com.example.timetracker.di.components.AppComponent
import com.example.timetracker.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {

    @Inject
    lateinit var preferences: TimeTrackerPreferences

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    init {
        App.appComponent?.inject(this)

        preferences.pin = "3831"
        Log.d("Prefs", preferences.pin ?: "")
    }
}