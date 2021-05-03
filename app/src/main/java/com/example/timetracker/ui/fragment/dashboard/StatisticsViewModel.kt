package com.example.timetracker.ui.fragment.dashboard

import androidx.lifecycle.MutableLiveData
import com.example.timetracker.App
import com.example.timetracker.R
import com.example.timetracker.common.extension.toFormattedString
import com.example.timetracker.data.db.model.Task
import com.example.timetracker.data.db.repository.TaskRepository
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.model.AlertObject
import java.util.*
import javax.inject.Inject

class StatisticsViewModel : BaseViewModel() {

    @Inject
    lateinit var taskRepository: TaskRepository

    val creatingTasksChartData = MutableLiveData<BarEntries>()
    val completedTasksChartData = MutableLiveData<BarEntries>()
    val allTasksCount = MutableLiveData<Int>()

    init {
        App.appComponent?.inject(this)
        fetchDataForCharts()
    }

    private fun fetchDataForCharts() {
        val flowable = taskRepository.getTasksAsync()

        flowable?.let { flowable ->
            compositeDisposable.add(flowable.subscribe({ fetchedTasks ->
                allTasksCount.value = fetchedTasks.count()

                updateCreatingTaskChart(fetchedTasks)
                updateCompletedTaskChart(fetchedTasks)
            }, {
                this.onAlertDialogNeeded.value = AlertObject("Error calculating statistics")
            }))
        }
    }

    private fun updateCreatingTaskChart(tasks: List<Task>) {
        val colorList = List(7) { resourceGetter.getColor(R.color.colorPrimary) }

        val valuesList = mutableListOf<Pair<String, Float>>()

        for (dayNum in (6 downTo 0)) {
            val neededDay = Calendar.getInstance()
            neededDay.add(Calendar.DAY_OF_MONTH, -dayNum)
            val formattedDate = neededDay.time.toFormattedString()
            val label = if (dayNum == 0) "Current \nday" else ""
            valuesList.add(Pair(label, tasks.count {
                val taskFormattedDay = it.createdAt?.toFormattedString()
                taskFormattedDay == formattedDate
            }.toFloat()))
        }

        creatingTasksChartData.value = BarEntries(colorList, valuesList)
    }

    private fun updateCompletedTaskChart(tasks: List<Task>) {
        val colorList = List(2) { resourceGetter.getColor(R.color.colorPrimary) }

        val valuesList = mutableListOf<Pair<String, Float>>()

        valuesList.add(Pair("Finished", tasks.count { it.isDone }.toFloat()))
        valuesList.add(Pair("Unfinished", tasks.count { !it.isDone }.toFloat()))

        completedTasksChartData.value = BarEntries(colorList, valuesList)
    }
}


data class BarEntries(val listOfColors: List<Int>, val listOfValues: List<Pair<String, Float>>)