package com.example.timetracker.ui.fragment.notifications

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentSettingsBinding
import com.example.timetracker.ui.base.BaseFragment
import com.example.timetracker.ui.base.BaseViewModel

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by viewModels()
    override fun baseViewModel(): BaseViewModel = viewModel

    override fun layoutRes(): Int = R.layout.fragment_settings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )

        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.settingsEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                SettingsEvents.CHANGE_PASSWORD -> showDialog()
                else -> {
                }
            }
        }
    }

    fun showDialog() {
        MaterialDialog(requireContext()).show {
            title(text = "Enter new pin")
            input(
                inputType = InputType.TYPE_CLASS_NUMBER,
                waitForPositiveButton = false
            ) { dialog, text ->
                if (text.length < 4) {
                    dialog.getInputField().error = "Min length - 4"
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, false)
                } else {
                    viewModel.changePin(text.toString())
                    dialog.dismiss()
                }
            }
            positiveButton(R.string.ok)
        }
    }
}