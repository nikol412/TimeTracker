package com.example.timetracker.data.db.repository

import com.example.timetracker.data.db.IRepository
import com.example.timetracker.data.db.model.Task
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class TaskRepository @Inject constructor() : IRepository {
    var realm: Realm? = Realm.getDefaultInstance()

    override fun close() {
        realm?.close()
    }

    fun createTask(task: Task) {
        realm?.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(task)
        }
    }

    fun getTasks(): RealmResults<Task>? {
        return realm?.where(Task::class.java)?.findAll()
    }

    fun getTasksAsync(): RealmResults<Task>? {
        return realm?.where(Task::class.java)?.findAllAsync()
    }
    fun removeTask(task: Task) {
        task.deleteFromRealm()
    }
}