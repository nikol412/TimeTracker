package com.example.timetracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.timetracker.ui.fragment.OkDialogFragment
import dagger.android.support.DaggerFragment

abstract class BaseFragment : Fragment() {

    abstract fun baseViewModel(): BaseViewModel

    @LayoutRes
    abstract fun layoutRes(): Int

    fun getBaseActivity(): BaseActivity = activity as BaseActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModelsObservables()
    }

    protected open fun subscribeToViewModelsObservables() {
        with(baseViewModel()) {
            onAlertDialogNeeded.observe(viewLifecycleOwner, Observer { alert ->
                showAlertDialogFragment(alert.title, alert.message, alert.onYesClick)
            })
        }
    }

    protected fun showAlertDialogFragment(title: String, message: String? = null, onYesClick: (() -> Unit)? = null) {
        OkDialogFragment(
            title = title,
            description = message,
            onYesClick = onYesClick
        ).show(requireActivity().supportFragmentManager, "")
    }
}