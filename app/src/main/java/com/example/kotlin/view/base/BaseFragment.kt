package com.example.kotlin.view.base

import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

open class BaseFragment : Fragment() {
    fun getAppCompatActivity() = (activity as AppCompatActivity)

    fun getSupportActionBar(toolbar: Toolbar): ActionBar? {
        getAppCompatActivity().setSupportActionBar(toolbar)
        return getAppCompatActivity().supportActionBar
    }
}