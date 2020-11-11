package com.example.timetracker.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.timetracker.R
import com.example.timetracker.common.extension.show
import kotlinx.android.synthetic.main.fragment_ok_dialog.*

class OkDialogFragment(
    private val title: String,
    private val description: String? = null,
    private val okButtonName: String = "Ok",
    private val onYesClick: (() -> Unit)? = null
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        return inflater.inflate(R.layout.fragment_ok_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.text = title
        bOk.text = okButtonName
        description?.let {
            tvDescription?.show()
            tvDescription?.text = description
        }
        bOk.setOnClickListener {
            onYesClick?.invoke()
            dismiss()
        }
    }
}