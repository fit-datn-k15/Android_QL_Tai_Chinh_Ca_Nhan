package com.example.quanlythuchi.view.main.report

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.entity.TotalCategory
import com.example.quanlythuchi.databinding.FagmentReportBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.view.adapter.AdapterTotalCategory
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class FragmentReport : BaseFragment<FagmentReportBinding, ReportViewModel>(), ReportListener,
    AdapterTotalCategory.OnClickListener{
    override val layoutID: Int = R.layout.fagment_report
    override val viewModel: ReportViewModel by activityViewModels()
    private val adapterViewpager by lazy { AdapterReport(this) }
    private val adapterRcv by lazy { AdapterTotalCategory(this) }
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

        viewBinding.apply {
            // adapterRcv.submitList(viewModel.dataExpenseRcv.value)
            rcv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rcv.adapter = this@FragmentReport.adapterRcv
        }
        viewModel.dataRcv.observe(viewLifecycleOwner) {
            adapterRcv.submitList(it)
        }
        viewModel.rcvExpensePrepare(YearMonth.now())
        viewBinding.rcv.isNestedScrollingEnabled = false;
        val itemDeclaration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        viewBinding.rcv.addItemDecoration(itemDeclaration)
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

    override fun onClickItemEI(item: TotalCategory) {

    }
}