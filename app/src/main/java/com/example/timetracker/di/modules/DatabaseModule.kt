package com.example.timetracker.di.modules

import android.content.Context
import android.util.Log
import com.example.timetracker.data.preferences.TimeTrackerPreferences
import com.example.timetracker.data.preferences.TimeTrackerPreferencesImpl
import dagger.Module
import dagger.Provides
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import java.security.SecureRandom
import javax.inject.Singleton

@Module
class DatabaseModule(val context: Context) {

    init {
        Realm.init(context)
        createDefaultRealmConfig()
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    private fun createDefaultRealmConfig() {
//        val key = ByteArray(64)
//
//        SecureRandom().nextBytes(key)

        val config = RealmConfiguration.Builder()
            .name("time_tracker.realm")
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    @Provides
    @Singleton
    fun providePreferences(): TimeTrackerPreferences {
        return TimeTrackerPreferencesImpl(
            context.applicationContext.getSharedPreferences("TimeTrackerPreds", Context.MODE_PRIVATE)
        )
    }
}