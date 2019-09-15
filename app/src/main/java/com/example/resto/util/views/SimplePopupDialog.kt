package com.example.resto.util.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.resto.R
import com.example.resto.databinding.DialogSimplePopupBinding


class SimplePopupDialog : DialogFragment() {
    private lateinit var binding: DialogSimplePopupBinding
    var listener: SimplePopupListeners? = null

    private val buttonName = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val title = MutableLiveData<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_simple_popup, container, false)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false

        initViews()
    }

    private fun initViews() {
        binding.actionButton.setOnClickListener {
            dismiss()
            listener?.onActionButton()
        }
        binding.exitIcon.setOnClickListener {
            dismiss()
            listener?.onCancel()
        }
        buttonName.observe(this, Observer {
            binding.actionButton.text = it
        })
        title.observe(this, Observer {
            binding.title.text = it
        })
        description.observe(this, Observer {
            binding.messageTV.text = it
        })
    }

    fun initAndShowDialog(fragmentManager: FragmentManager, tag: String = "", actionButtonName: String? = null,
                          message: String, dialogTitle: String = "") {
        show(fragmentManager, tag)
        initDialogTexts(actionButtonName, message, dialogTitle)
    }

    private fun initDialogTexts(actionButtonName: String? = null,
                                message: String, dialogTitle: String) {
        actionButtonName?.let { buttonName.value = it }
        description.value = message
        title.value = dialogTitle
    }

    interface SimplePopupListeners {
        fun onActionButton()
        fun onCancel()
    }
}