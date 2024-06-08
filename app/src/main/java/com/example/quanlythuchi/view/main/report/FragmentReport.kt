package com.example.quanlythuchi.view.main.report

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FagmentReportBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.view.adapter.AdapterExpenseIncomeReport
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
@AndroidEntryPoint
class FragmentReport : BaseFragment<FagmentReportBinding, ReportViewModel>(), ReportListener,
    AdapterExpenseIncomeReport.OnClickListener{
    override val layoutID: Int = R.layout.fagment_report
    override val viewModel: ReportViewModel by activityViewModels()
    private val adapterViewpager by lazy { AdapterReport(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            viewModel = this@FragmentReport.viewModel
        }
        viewModel.getAllData()
        setTimeDefault()
        viewBinding.apply {
            listener = this@FragmentReport
            vpg.adapter = this@FragmentReport.adapterViewpager
            TabLayoutMediator(tabLayoutReport, vpg) { tab, position ->
                if (position == 0)
                    tab.text = context?.getString(R.string.expense)
                else
                    tab.text = context?.getString(R.string.income)
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

    override fun onClickItemEI(item: ExpenseIncome) {

    }
}