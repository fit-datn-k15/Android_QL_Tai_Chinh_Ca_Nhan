package com.example.quanlythuchi.view.fragment.report

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.databinding.FagmentReportBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate

class FragmentReport : BaseFragment<FagmentReportBinding, ReportViewModel>(), ReportListener {
    override val layoutID: Int = R.layout.fagment_report
    override val viewModel: ReportViewModel by activityViewModels()
    private val adapterViewpager by lazy { AdapterReport(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimeDefault()
        viewBinding.apply {
            listener = this@FragmentReport
            vpg.adapter = this@FragmentReport.adapterViewpager
            TabLayoutMediator(tabLayoutReport, vpg) { tab, position ->
                if (position == 0)
                    tab.text = Constance.EXPENSE
                else
                    tab.text = Constance.INCOME
            }.attach()
        }


    }
    override fun openDayPicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                viewModel.apply {
                    date = LocalDate.of(year, month+1, dayOfMonth)
                }
                viewBinding.pickTime.text = viewModel.date.formatDateTime()
            },
            viewModel.date.year,
            viewModel.date.monthValue -1,
            viewModel.date.dayOfMonth
        )
        picker.show()

    }

    override fun btnBackDay() {

    }

    override fun btnNextDay() {

    }

    private fun setTimeDefault() {
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time

    }
}