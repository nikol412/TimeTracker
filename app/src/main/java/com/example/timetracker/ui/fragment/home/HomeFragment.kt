package com.example.timetracker.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentHomeBinding
import com.example.timetracker.ui.base.BaseFragment
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.fragment.home.createTask.CreateTaskBottomSheetDialogFragment

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_home

    lateinit var binding: FragmentHomeBinding

    val adapter: ItemsAdapter by lazy { ItemsAdapter() }
    val todayAdapter: ItemsAdapter by lazy { ItemsAdapter() }


    private val createTaskFragment by lazy {
        CreateTaskBottomSheetDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.recyclerViewTodayTasks.adapter = todayAdapter
        binding.homeRecyclerView.adapter = adapter

        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tasks.observe(viewLifecycleOwner, { listOfTasks ->
            adapter.setItems(listOfTasks)
        })

        viewModel.todayTasks.observe(viewLifecycleOwner, { tasks ->
            todayAdapter.setItems(tasks)

            if (tasks.isEmpty()) {
                binding.cardViewTodayTasks.visibility = View.GONE
            }
        })
        binding.fabCreateCard.setOnClickListener {
            createTaskFragment.show(childFragmentManager, "bottomSheet")
        }
    }
}