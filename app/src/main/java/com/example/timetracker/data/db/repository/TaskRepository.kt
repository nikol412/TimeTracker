package com.example.timetracker.data.db.repository

import com.example.timetracker.data.db.IRepository
import com.example.timetracker.data.db.model.Task
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor() : IRepository {

    var realm = Realm.getDefaultInstance()

    fun createTask(task: Task) {
        realm.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(task)
        }
    }

    fun createTask(title:String, category: String, createdAt: Date, description: String) {
        createTask(Task(title = title,
            category = category,
            createdDate = createdAt,
            description = description
        ))
    }

    fun getTasksByDate(): RealmResults<Task>? {
        return realm.where(Task::class.java)
            .sort(Task::createdAt.name, Sort.DESCENDING)
            .findAllAsync()
    }

    fun getTasks(): RealmResults<Task> {
        return realm.where(Task::class.java)
            .findAll()
    }


    override fun close() {
        realm.close()
    }
}