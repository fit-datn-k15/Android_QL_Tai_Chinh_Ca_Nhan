package com.example.quanlythuchi.view.fragment.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FagmentReportBinding

class FragmentReport : BaseFragment<FagmentReportBinding,ReportViewModel>(),ReportListener {
    override val layoutID: Int = R.layout.fagment_report
    override val viewModel : ReportViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {
            listener = this@FragmentReport
        }

    }
}