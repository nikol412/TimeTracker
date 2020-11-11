package com.example.timetracker.ui.base

import android.app.Activity
import android.content.Intent
import dagger.android.support.DaggerAppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeToViewModelObservables()
    }

    protected open fun subscribeToViewModelObservables() {

    }

    fun startActivity(activity: Class<out Activity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}