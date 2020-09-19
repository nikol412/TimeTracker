package com.example.timetracker.data.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User: RealmObject {
    @PrimaryKey
    private var dbId = 0

    var firstName: String = ""
    var lastName: String? = null


    constructor() : super()

    constructor(variant: Int) {
        this.firstName = "$variant".repeat(3)
        this.lastName = "$variant".repeat(7)
    }

    override fun equals(other: Any?): Boolean {
        if( this === other) return true
        if (other !is User) return false

        if(firstName != other.firstName) return false
        if(lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + (lastName?.hashCode() ?: 0)
        return result
    }
}