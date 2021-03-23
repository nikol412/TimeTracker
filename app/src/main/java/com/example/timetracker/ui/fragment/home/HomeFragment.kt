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
import com.example.timetracker.ui.fragment.newItem.NewItemBottomSheetDialog
import com.example.timetracker.ui.fragment.home.createTask.CreateTaskBottomSheetDialogFragment

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_home

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ItemsAdapter

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
        adapter = ItemsAdapter()

        binding.homeRecyclerView.adapter = adapter

        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
//        NewItemBottomSheetDialog().show(fragmentTransaction, "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tasks.observe(viewLifecycleOwner, { listOfTasks ->
            adapter.setItems(listOfTasks)
        })
        binding.fabCreateCard.setOnClickListener {
            createTaskFragment.show(childFragmentManager, "bottomSheet")
        }
    }
}