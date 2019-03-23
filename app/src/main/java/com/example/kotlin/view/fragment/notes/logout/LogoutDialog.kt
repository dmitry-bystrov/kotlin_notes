package com.example.kotlin.view.fragment.notes.logout

import android.os.Bundle
import android.view.View
import com.example.kotlin.R
import com.example.kotlin.view.base.BaseBottomDialog
import kotlinx.android.synthetic.main.dialog_logout.*

class LogoutDialog : BaseBottomDialog() {
    companion object {
        fun newInstance() = LogoutDialog()
    }

    var logoutListener: LogoutListener? = null

    override val layoutRes: Int = R.layout.dialog_logout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout_button.setOnClickListener { logoutListener?.onLogout() }
        cancel_button.setOnClickListener { dismiss() }
    }

    interface LogoutListener {
        fun onLogout()
    }
}