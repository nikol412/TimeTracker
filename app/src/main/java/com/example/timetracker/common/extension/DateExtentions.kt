package com.example.timetracker.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    return sdf.parse(this)
}