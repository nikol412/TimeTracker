package com.example.timetracker.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.data.db.model.User
import com.example.timetracker.data.db.repository.UserRepository
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.model.AlertObject
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        App.appComponent?.inject(this)

        userRepository.createUser(User(1))
        userRepository.getUserAsync()?.addChangeListener<User> { data, changeSet ->
            _text.value = data.firstName + data.lastName
            onAlertDialogNeeded.value = AlertObject("Success", "User was created")
        }


    }
}