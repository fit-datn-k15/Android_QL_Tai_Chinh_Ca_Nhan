package com.example.quanlythuchi.view.fragment.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FagmentCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
@AndroidEntryPoint
class CalendarFragment : BaseFragment<FagmentCalendarBinding,CalendarViewModel>(),CalendarListener {
    override val layoutID: Int = R.layout.fagment_calendar
    override val viewModel: CalendarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@CalendarFragment
            viewModel = this.viewModel
        }
        viewModel.getDataByDate()
        viewBinding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.date = LocalDate.of(year,month+1,dayOfMonth)
            viewModel.isGetDataByDate.postValue(false)
            viewModel.getDataByDate()
            viewBinding.viewModel = this@CalendarFragment.viewModel
        }
        viewModel.isGetDataByDate.observe(viewLifecycleOwner) {
            if(it) {
                viewBinding.viewModel = this.viewModel
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.resetData()
    }

}


