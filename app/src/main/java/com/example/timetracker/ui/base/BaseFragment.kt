package com.example.timetracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
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
            // TODO: implement this after implementing baseViewModel
        }
    }

    protected fun showAlertDialogFragment(title: String? = null, message: String? = null) {
        // TODO: implement this after implementing baseViewModel
    }
}