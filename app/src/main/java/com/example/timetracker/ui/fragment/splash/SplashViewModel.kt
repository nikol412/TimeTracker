package com.example.timetracker.ui.fragment.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.timetracker.App
import com.example.timetracker.R
import com.example.timetracker.common.live.SingleLiveEvent
import com.example.timetracker.data.preferences.TimeTrackerPreferences
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.model.AlertObject
import javax.inject.Inject

class SplashViewModel : BaseViewModel() {

    @Inject
    lateinit var preferences: TimeTrackerPreferences

    val events = SingleLiveEvent<SplashActions>()

    val pinEnterVisibility = MutableLiveData(false)
    val pinInput = MutableLiveData<String>()

    private val pinObserver = Observer<String> { pin ->
        if (pin?.length == 4) onPinEntered(pin)
    }

    init {
        App.appComponent?.inject(this)
        //configureLogin()

        pinInput.observeForever(pinObserver)
    }

    override fun onCleared() {
        pinInput.removeObserver(pinObserver)
        super.onCleared()
    }

    fun configureLogin() {
        if (preferences.isFirstEnter) navigateToLogin()

        if (preferences.isUseBiometric) authViaBiometric()

        authViaPin()
    }

    private fun navigateToLogin() {
        navigateTo(R.id.loginFragment)
    }

    private fun authViaBiometric() {
        events.value = SplashActions.USE_BIOMETRIC
    }

    private fun authViaPin() {
        pinEnterVisibility.value = true

        events.value = SplashActions.USE_PIN
    }

    private fun onPinEntered(enteredPin: String) {
        if (validatePin(enteredPin)) {
            onAuthSuccess()
        } else {
            onAuthError()
        }
    }

    private fun onAuthSuccess() {
        navigateTo(R.id.navigation_home)
    }

    private fun onAuthError() {
        pinInput.value = ""
        onAlertDialogNeeded.value = AlertObject("Invalid pin")
    }

    private fun validatePin(enteredPin: String): Boolean {
        return preferences.pin == enteredPin
    }

    fun onUsePinClick() {
        authViaPin()
    }
}

enum class SplashActions {
    USE_PIN, USE_BIOMETRIC
}