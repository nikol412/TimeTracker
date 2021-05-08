package com.example.timetracker.ui.fragment.splash.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.App
import com.example.timetracker.R
import com.example.timetracker.data.preferences.TimeTrackerPreferences
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.model.AlertObject
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {
    val pin = MutableLiveData("")
    val login = MutableLiveData("")

    @Inject
    lateinit var preferences: TimeTrackerPreferences

    init {
        App.appComponent?.inject(this)
    }

    fun onSignUpClick() {
        if (validateLogin(login.value ?: "") &&
            validatePin(pin.value ?: "")
        ) {
            savePreferences(login.value!!, pin.value!!)
            navigateTo(R.id.navigation_home)

        } else {
            onAlertDialogNeeded.value = AlertObject("Invalid login or pin")
        }
    }

    private fun savePreferences(login: String, pin: String) {
        with(preferences) {
            this.login = login
            this.pin = pin
            this.isFirstEnter = false
        }
    }

    private fun validateLogin(login: String) = login.isNotBlank()
    private fun validatePin(pin: String) = pin.isNotBlank() && pin.length == 4

}