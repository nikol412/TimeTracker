package com.example.timetracker.di.modules

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import java.security.SecureRandom

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
        deleteDBFile()
        val key = ByteArray(64)
        SecureRandom().nextBytes(key)
        val config = RealmConfiguration.Builder()
            .name("time_tracker.realm")
            .apply {
                if(BuildConfig.DEBUG.not()) {
                    inMemory()
                    encryptionKey(key)
                }
            }
        //TODO REMINDER: after release use migrations
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    private fun deleteDBFile() {
        //if (BuildConfig.DEBUG.not()) {
        try {
            val realmFile = File("/data/data/${context.packageName}/files/98Gym.realm")
            if (realmFile.exists()) {
                realmFile.delete()
            }
        } catch (ex: Exception) {
            Log.e("DatabaseModule", ex.message, ex)
        }
        //}
    }
}