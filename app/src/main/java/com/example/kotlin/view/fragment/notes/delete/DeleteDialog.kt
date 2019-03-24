package com.example.kotlin.view.fragment.notes.delete

import android.os.Bundle
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.view.base.BaseBottomDialog
import kotlinx.android.synthetic.main.dialog_logout.*

class DeleteDialog : BaseBottomDialog() {
    companion object {
        fun newInstance() = DeleteDialog()
    }

    var deleteListener: DeleteListener? = null

    override val layoutRes: Int = R.layout.dialog_delete

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout_button.setOnClickListener {
            deleteListener?.onDelete()
            dismiss()
        }
        cancel_button.setOnClickListener { dismiss() }
    }

    interface DeleteListener {
        fun onDelete()
    }
}