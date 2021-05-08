package com.example.timetracker.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.timetracker.R
import com.example.timetracker.common.BIOMETRIC_AUTH_RESULT
import com.example.timetracker.common.BiometricAuth
import com.example.timetracker.databinding.FragmentSplashBinding
import com.example.timetracker.ui.base.BaseFragment
import com.example.timetracker.ui.base.BaseViewModel

class SplashFragment : BaseFragment() {

    lateinit var binding: FragmentSplashBinding

    private val viewModel: SplashViewModel by viewModels()
    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_splash

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.vm = viewModel
        viewModel.navController = findNavController()
        viewModel.configureLogin()

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                SplashActions.USE_BIOMETRIC -> {
                    askForBiometricAuth()
                }
                SplashActions.USE_PIN -> {
                    binding.editTextPin.requestFocus()
                }
                else -> {
                    //ignore
                }
            }
        }
    }

    private fun askForBiometricAuth() {
        if (BiometricAuth.isBiometricAvailable(requireContext()) && viewModel.checkIsUseBiometric()) {
            BiometricAuth.launchBiometricAuth(requireContext(), this) { result ->
                when(result) {
                    BIOMETRIC_AUTH_RESULT.SUCCESS -> viewModel.navigateToHome()
                }
            }
        }
    }

}