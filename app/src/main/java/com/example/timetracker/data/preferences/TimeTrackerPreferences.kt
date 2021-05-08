package com.example.timetracker.data.preferences

interface TimeTrackerPreferences {

    var isUseBiometric: Boolean

    var isFirstEnter: Boolean

    var login: String?

    var pin: String?

    fun clearPreferences()
}