package com.example.timetracker.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var adapter: ItemsAdapter

    private val createTaskFragment by lazy {
        CreateTaskBottomSheetDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = ItemsAdapter(object: OnItemDismiss {
            override fun onDismiss(position: Int) {
                viewModel.markTaskAsDone(position)
            }
        })

        configureTouchHelper(adapter, binding.homeRecyclerView)

        binding.homeRecyclerView.adapter = adapter

        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tasks.observe(viewLifecycleOwner) { listOfTasks ->
            adapter.setItems(listOfTasks)
        }
        binding.fabCreateCard.setOnClickListener {
            createTaskFragment.show(childFragmentManager, "bottomSheet")
        }
    }

    private fun configureTouchHelper(adapter: ItemsAdapter, recyclerView: RecyclerView) {
        val callback = TaskTouchHelperImpl(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
    }
}

class TaskTouchHelperImpl(private val touchHelperAdapter: TaskTouchHelperAdapter): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.END

        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        touchHelperAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}

interface TaskTouchHelperAdapter {
    fun onItemDismiss(position: Int)
}