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

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_home

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = ItemsAdapter()
        adapter.setItems(viewModel.items)
        binding.homeRecyclerView.adapter = adapter

        return binding.root
    }
}