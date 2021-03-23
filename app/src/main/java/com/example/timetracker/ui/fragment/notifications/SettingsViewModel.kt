package com.example.timetracker.ui.fragment.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetracker.ui.base.BaseViewModel

class SettingsViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    init {

    }
}