package com.example.timetracker

import android.app.Activity
import android.app.Application
import com.example.timetracker.di.components.AppComponent
import com.example.timetracker.di.components.DaggerAppComponent
import com.example.timetracker.di.modules.DatabaseModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    companion object {
        var appComponent: AppComponent? = null
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .databaseModule(DatabaseModule(applicationContext))
            .build()
        appComponent?.inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector
}