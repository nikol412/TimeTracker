package com.example.timetracker.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.timetracker.R
import com.example.timetracker.common.extension.hide
import com.example.timetracker.common.extension.show
import com.example.timetracker.databinding.ActivityMainBinding
import com.example.timetracker.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    // add fragments which should be without nav bar
    private val NEED_HIDE_NAV_BAR =
        setOf(1)


    override fun layoutRes(): Int = R.layout.activity_main

    private val viewModel: MainVM by viewModels()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.lifecycleOwner = this

        setupBottomNavigationBar()
        configureNavController()
    }


    fun setupBottomNavigationBar() {
        binding.bottomNavView.itemIconTintList = null

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavView.setupWithNavController(navController)
    }

    fun configureNavController() {
        findNavController(R.id.nav_host_fragment)?.let {  navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if(destination.id in NEED_HIDE_NAV_BAR) {
                    binding.bottomNavView.hide()
                } else {
                    binding.bottomNavView.show()
                }
            }
        }
    }
}