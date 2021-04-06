package com.example.timetracker.data.preferences

interface TimeTrackerPreferences {

    var isUseBiometric: Boolean

    var isFirstEnter: Boolean

    var isTodaySectionEnabled: Boolean

    var isMoveStarredTasksToTop: Boolean

    var confirmBeforeDeleting: Boolean

    var pin: String?

    fun clearPreferences()
}