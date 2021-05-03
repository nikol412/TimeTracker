package com.example.timetracker.data.db.model

import com.example.timetracker.common.extension.toDate
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Task : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var date: Date? = null
    var description: String = ""

    var createdAt: Date? = null

    var isDone: Boolean = false
    var doneDate: String ? = null

    constructor() : super()

    constructor(title: String, createdDate: String, description: String) {
        this.title = title
        try {
            this.date = createdDate.toDate()
        } catch (e: Exception) {
            return
        }
        this.description = description
    }

    constructor(title: String, targetDate: String, description: String, createdAt: Date) {
        this.title = title
        try {
            this.date = targetDate.toDate()
        } catch (e: Exception) {
            return
        }

        this.createdAt = createdAt
        this.description = description
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is Task) return false
        if (id != other.id) return false
        if (title != other.title) return false
        if (date != other.date) return false
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (isDone != other.isDone) return false
        if (doneDate != other.doneDate) return false

        return true
    }
}