package com.example.timetracker.data.db.model

import com.example.timetracker.common.extension.toDate
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Task : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var createdAt: Date? = null
    var description: String = ""

    constructor() : super()

    constructor(title: String, createdDate: String, description: String) {
        this.id = createdDate.hashCode()
        this.title = title
        try {
            this.createdAt = createdDate.toDate()
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
        if (createdAt != other.createdAt) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = result * 31 + title.hashCode()
        result = result * 31 + createdAt.hashCode()
        result = result * 31 + description.hashCode()
        return result
    }
}