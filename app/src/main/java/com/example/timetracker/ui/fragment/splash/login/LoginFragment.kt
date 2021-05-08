package com.example.timetracker.ui.fragment.splash.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentLoginBinding
import com.example.timetracker.ui.base.BaseFragment
import com.example.timetracker.ui.base.BaseViewModel

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_login

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.vm = viewModel
        viewModel.navController = findNavController()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}