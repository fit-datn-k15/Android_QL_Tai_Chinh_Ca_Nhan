package com.example.quanlythuchi.view.fragment.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FagmentCalendarBinding

class CalendarFragment : BaseFragment<FagmentCalendarBinding,CalendarViewModel>() {
    override val layoutID: Int = R.layout.fagment_calendar
    override val viewModel: CalendarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {

        }
    }
}


