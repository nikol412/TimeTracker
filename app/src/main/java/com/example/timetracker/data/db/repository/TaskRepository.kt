package com.example.timetracker.data.db.repository

import com.example.timetracker.data.db.IRepository
import com.example.timetracker.data.db.model.Task
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

class TaskRepository @Inject constructor() : IRepository {

    var realm = Realm.getDefaultInstance()

    fun createTask(task: Task) {
        realm.executeTransactionAsync { realm ->
            realm.where(Task::class.java).max(Task::id.name)?.let { currentId ->
                task.id = currentId.toInt() + 1
                realm.insertOrUpdate(task)

            } ?: kotlin.run {
                task.id = 0
                realm.insertOrUpdate(task)
            }
        }
    }

    fun getTasksByDate(): Flowable<RealmResults<Task>>? {
        return realm.where(Task::class.java)
            .sort(Task::date.name, Sort.DESCENDING)
            .equalTo(Task::isDone.name, false)
            .findAllAsync()
            .asFlowable()
    }

    fun getTasksAsync(): Flowable<RealmResults<Task>>? {
        return realm.where(Task::class.java)
            .equalTo(Task::isDone.name, false)
            .findAllAsync()
            .asFlowable()
    }

    fun cleanTasksAsync() {
        realm.executeTransactionAsync {
            it.deleteAll()
        }
    }

    fun markAsDone(task: Task, date: String) {
        realm.executeTransactionAsync {
            it.where(Task::class.java)
                .equalTo(Task::id.name, task.id)
                .findAllAsync()
                .forEach { task ->
                    task.isDone = true
                    task.doneDate = date
                }
        }
    }


    override fun close() {
        realm.close()
    }
}