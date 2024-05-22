package com.example.quanlythuchi.view.main.calendar

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.quanlythuchi.R
import com.example.quanlythuchi.extension.makeGone
import com.example.quanlythuchi.extension.makeVisible

interface HasToolbar {
    val toolbar: Toolbar? // Return null to hide the toolbar
}

interface HasBackButton

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    override fun onStart() {
        super.onStart()
        if (this is HasToolbar) {
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        }

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if (this is HasBackButton) {
            actionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onStop() {
        super.onStop()
        if (this is HasToolbar) {

        }
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if (this is HasBackButton) {
            actionBar?.title = context?.getString(R.string.activity_title_view)
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
