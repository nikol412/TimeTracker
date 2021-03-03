package com.example.timetracker.data.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Task : RealmObject {

    @PrimaryKey
    private var id = 0

    var title: String = ""
    var date: String = ""

    var description: String = ""

    constructor(): super()

    constructor(title: String, date: String, description: String) {
        this.title = title
        this.date = date
        this.description = description
    }

    override fun equals(other: Any?): Boolean {

        if(this === other) return true
        if (other !is Task) return false

        if(title != other.title) return false
        if(date != other.date) return false
        if(description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + description.hashCode()

        return result
    }
}