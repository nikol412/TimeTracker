package com.example.timetracker.common.extension

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    return sdf.parse(this)
}

fun Date.toFormattedString(): String {
    return android.text.format.DateFormat.format("dd/MM/yyyy", this).toString()
}