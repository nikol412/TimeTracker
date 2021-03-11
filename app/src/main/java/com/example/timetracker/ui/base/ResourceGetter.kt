package com.example.timetracker.ui.base

import android.content.Context
import javax.inject.Inject

class ResourceGetter @Inject constructor(private val context: Context) {
    fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getStringWithParams(stringResId: Int, args: Array<in Any>): String {
        return context.getString(stringResId, args)
    }

    fun getColor(colorResId: Int): Int {
        return context.getColor(colorResId)
    }
}