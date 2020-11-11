package com.example.timetracker.ui.base

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.timetracker.App
import com.example.timetracker.common.live.SingleLiveEvent
import com.example.timetracker.ui.model.AlertObject
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel() : ViewModel() {

    lateinit var navController: NavController

    val onStartActivity = SingleLiveEvent<Class<out Activity>>()
    val isProgressInProcess = MutableLiveData(false)

    val onAlertDialogNeeded = SingleLiveEvent<AlertObject>()

    @Inject
    lateinit var resourceGetter: ResourceGetter

    protected val compositeDisposable = CompositeDisposable()

    init {
        App.appComponent?.inject(this)
    }

    fun navigateTo(actionId: Int) {
        navController.navigate(actionId)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}