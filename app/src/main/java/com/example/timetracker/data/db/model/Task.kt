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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is Task) return false
        if (id != other.id) return false
        if (title != other.title) return false
        if (date != other.date) return false
        if (description != other.description) return false

        return true
    }
}