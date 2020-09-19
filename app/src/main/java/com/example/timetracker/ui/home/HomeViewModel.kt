package com.example.timetracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.App
import com.example.timetracker.data.db.model.User
import com.example.timetracker.data.db.repository.UserRepository
import javax.inject.Inject

class HomeViewModel : ViewModel() {

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
        }
    }
}