package com.example.timetracker.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.data.db.model.User
import com.example.timetracker.data.db.repository.UserRepository
import com.example.timetracker.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    var homeLabel = MutableLiveData<String>()
    var items = mutableListOf(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20
    ).map { it.toString() }

    init {
        App.appComponent?.inject(this)

        userRepository.createUser(User(1))
        userRepository.getUserAsync()?.addChangeListener<User> { data, changeSet ->
        }
    }
}