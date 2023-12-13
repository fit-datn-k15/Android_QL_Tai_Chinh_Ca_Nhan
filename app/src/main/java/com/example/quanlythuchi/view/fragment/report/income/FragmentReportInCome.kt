package com.example.quanlythuchi.view.fragment.report.income

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentReportIncomeBinding
import com.example.quanlythuchi.view.fragment.report.ReportViewModel

class FragmentReportInCome : BaseFragment<FragmentReportIncomeBinding,ReportViewModel>(),
    ReportExpenseListener {
    override val layoutID: Int = R.layout.fragment_report_income
    override val viewModel : ReportViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {

        }

    }
}