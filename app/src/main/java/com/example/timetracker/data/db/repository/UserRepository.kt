package com.example.timetracker.data.db.repository

import com.example.timetracker.data.db.IRepository
import com.example.timetracker.data.db.model.User
import io.realm.Realm
import javax.inject.Inject

class UserRepository @Inject constructor() : IRepository {

    var realm: Realm? = Realm.getDefaultInstance()

    fun createUser(user: User) {
        realm?.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(user)
        }
    }

    fun getUser() = realm?.where(User::class.java)?.findFirst()

    fun getUserAsync() = realm?.where(User::class.java)?.findFirstAsync()

    fun deleteUser() {
        realm?.executeTransaction { realm -> realm.deleteAll() }
    }
    override fun close() {
        realm?.close()
    }
}