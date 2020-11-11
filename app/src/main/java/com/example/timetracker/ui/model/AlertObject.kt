package com.example.timetracker.ui.model

class AlertObject(var title: String? = null, var message: String? = null, val onYesClick: (() -> Unit)? = null)