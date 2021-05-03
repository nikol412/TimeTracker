package com.example.timetracker.ui.fragment.dashboard

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.R
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.ui.base.BaseViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.*
import javax.inject.Inject

class StatisticsViewModel : BaseViewModel() {

    @Inject
    lateinit var taskRepository: TaskRepository

    val pieData = MutableLiveData<PieData>()

    init {
        App.appComponent?.inject(this)
        fetchDataForPieChart()
    }

    fun fetchDataForPieChart() {

        var tasksWithPreviousDateCount = 0
        var tasksWithFutureDateCount = 0
        var tasksWithoutDate = 0

        taskRepository.getTasksAsync().let {
            val currentDate = Calendar.getInstance()
            tasksWithPreviousDateCount = it.count { task ->
                if (task.date == null) return@count false
                return@count task.date!! < currentDate.time
            }
            tasksWithFutureDateCount = it.count { task ->
                if (task.date == null) return@count false
                return@count task.date!! > currentDate.time
            }

            tasksWithoutDate = it.count { task ->
                if (task.date == null) return@count true
                return@count false
            }
        }
        val colorList = mutableListOf<Int>()
        colorList.add(resourceGetter.getColor(R.color.colorPrimary))
        colorList.add(Color.MAGENTA)
        colorList.add(resourceGetter.getColor(R.color.grayDark))

        val pieEntryList = mutableListOf<PieEntry>()
        pieEntryList.add(PieEntry(tasksWithFutureDateCount.toFloat(), "Предстоящие"))
        pieEntryList.add(PieEntry(tasksWithPreviousDateCount.toFloat(), "Прошедшие"))
        pieEntryList.add(PieEntry(tasksWithoutDate.toFloat(), "Без даты"))


        val pieDataSet = PieDataSet(pieEntryList, "Tasks")
        pieDataSet.valueTextSize = 12F
        pieDataSet.colors = colorList
        val newPieData = PieData(pieDataSet)
        newPieData.setDrawValues(true)

        pieData.value = newPieData

    }
}