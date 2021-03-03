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
    var items = (1..20).map { it.toString() }.toMutableList()

    init {
        App.appComponent?.inject(this)

        userRepository.createUser(User(1))
        userRepository.getUserAsync()?.addChangeListener<User> { data, changeSet ->
        }
    }
}