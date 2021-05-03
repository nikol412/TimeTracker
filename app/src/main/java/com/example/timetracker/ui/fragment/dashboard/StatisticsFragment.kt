 package com.example.timetracker.ui.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentStatisticsBinding
import com.example.timetracker.ui.base.BaseFragment
import com.example.timetracker.ui.base.BaseViewModel
import kotlin.math.roundToInt

 class StatisticsFragment : BaseFragment() {

    private val viewModel: StatisticsViewModel by viewModels()
    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_statistics

    lateinit var binding: FragmentStatisticsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

//        binding.williamChartDonut.show(listOf(4F, 5F))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.creatingTasksChartData.observe(viewLifecycleOwner, { dataSet ->
            with(binding.williamChartBar) {
                barsColorsList = dataSet.listOfColors
                labelsSize = 40F
                labelsFormatter = { it -> it.roundToInt().toString()}
                show(dataSet.listOfValues)
            }

        })

        viewModel.completedTasksChartData.observe(viewLifecycleOwner, { data ->
            with(binding.chartCompletedTasks) {
                barsColor = data.listOfColors.first()
                labelsSize = 40F
                labelsFormatter = { it -> it.roundToInt().toString()}
                show(data.listOfValues)
            }
        })

        viewModel.allTasksCount.observe(viewLifecycleOwner, { tasksCount ->
            binding.textViewCompletedTasksCount.text = "Number of \nall tasks:\n${tasksCount ?: 0}"
        })

    }

}