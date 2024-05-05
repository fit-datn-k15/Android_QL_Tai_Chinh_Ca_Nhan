package com.example.quanlythuchi.view.main.report.expense

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentReportExpenseBinding
import com.example.quanlythuchi.view.main.report.ReportViewModel
import com.example.quanlythuchi.view.main.report.income.ReportExpenseListener

class FragmentReportExpense : BaseFragment<FragmentReportExpenseBinding, ReportViewModel>(),
    ReportExpenseListener {
    override val layoutID: Int = R.layout.fragment_report_expense
    override val viewModel : ReportViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {

        }

    }
}