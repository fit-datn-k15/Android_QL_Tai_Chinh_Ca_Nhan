package com.qltc.finace.view.main.report

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import com.qltc.finace.R
import com.qltc.finace.base.BaseFragment
import com.qltc.finace.base.Constant
import com.qltc.finace.data.entity.CategoryOverView
import com.qltc.finace.databinding.FagmentReportBinding
import com.qltc.finace.extension.formatDateTime
import com.qltc.finace.extension.formatMonthVN
import com.qltc.finace.extension.navigateWithAnim
import com.qltc.finace.view.adapter.AdapterTotalCategory
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class FragmentReport : BaseFragment<FagmentReportBinding, ReportViewModel>(), ReportListener,
    AdapterTotalCategory.OnClickListener, OnChartValueSelectedListener {
    companion object {
        const val CHOOSE_EXPENSE = 0
        const val CHOOSE_INCOME = 1
    }
    override val layoutID: Int = R.layout.fagment_report
    override val viewModel: ReportViewModel by activityViewModels()
    private val adapterRcv by lazy { AdapterTotalCategory(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            viewModel = this@FragmentReport.viewModel
        }
        viewModel.getAllData (callBack = {
            if (it == CHOOSE_EXPENSE) {
                viewModel.prepareDataPieChartExpenseByMonth(YearMonth.from(viewModel.date))
            }
            else if (it == CHOOSE_INCOME) {
              //  viewModel.filterDataIncomeByMonth(YearMonth.from(viewModel.date))
            }
        })
        setTimeDefault()
        setUpTabLayout()
        setUpRecyclerView()
        setUpPieChart()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }
    override fun openDayPicker() {
        DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                viewModel.date = LocalDate.of(year, month+1, dayOfMonth)
                if (viewModel?.typeReport?.value == CHOOSE_EXPENSE)
                    viewModel.prepareDataPieChartExpenseByMonth(YearMonth.from(viewModel?.date))
                else
                  //  viewModel.filterDataIncomeByMonth(YearMonth.from(viewModel.date))
                viewBinding.monthSelected.text = getString(R.string.month) + YearMonth.from(viewModel.date).formatMonthVN()
            },
            viewModel.date.year,
            viewModel.date.monthValue -1,
            viewModel.date.dayOfMonth
        ).show()
    }

    override fun openViewAll() {
        findNavController().navigateWithAnim(R.id.frg_all_income_expense, bundleOf())
    }

    private fun setTimeDefault() {
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time
    }

    override fun onClickItemEI(item: CategoryOverView) {
        findNavController().navigateWithAnim(R.id.frg_list_data, bundleOf(
            Constant.KEY_ITEM_CATEGORY_OF_DATA to item.category.idCategory,
            Constant.DATA to item.listRecord,
            Constant.TITLE_CATEGORY to item.category.title
        ))
    }
    private fun setUpTabLayout() {
        viewBinding.apply {
            listener = this@FragmentReport
            tabLayoutReport.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(p0: TabLayout.Tab?) {
                    if (p0?.position == CHOOSE_EXPENSE) {
                        viewModel?.typeReport?.value = CHOOSE_EXPENSE
                    }
                    else if (p0?.position == CHOOSE_INCOME) {
                        viewModel?.typeReport?.value = CHOOSE_INCOME
                    }
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {}

                override fun onTabReselected(p0: TabLayout.Tab?) {}
            })
        }
    }
    private fun setUpRecyclerView() {
        viewBinding.apply {
            rcv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rcv.adapter = this@FragmentReport.adapterRcv
        }
        viewModel.dataRcv.observe(viewLifecycleOwner) {
            adapterRcv.submitList(it)
        }
        viewBinding.apply {
            rcv.isNestedScrollingEnabled = false;
            val itemDeclaration =
                DividerItemDecoration(this@FragmentReport.context, DividerItemDecoration.VERTICAL)
            rcv.addItemDecoration(itemDeclaration)
            monthSelected.text =
                context?.getString(R.string.month) + YearMonth.from(viewModel?.date).formatMonthVN()
        }

    }
    private fun setUpPieChart() {
        viewBinding.mChart.apply {
            setPieChartReportDefault()
            setOnChartValueSelectedListener(this@FragmentReport)
            viewModel.dataPieChar.value?.let { submitList(it) }
        }
        viewModel.dataPieChar.observe(viewLifecycleOwner) {
            viewBinding.mChart.submitList(it)
            notifyRecyclerViewNeedUpdate()
        }
        viewModel.prepareDataPieChartExpenseByMonth(YearMonth.from(viewModel.date))
    }
    private fun notifyRecyclerViewNeedUpdate() {
        viewModel.prepareRecyclerViewExpense(YearMonth.from(viewModel.date))
    }
    override fun onValueSelected(p0: Entry?, p1: Highlight?) {

    }

    override fun onNothingSelected() {

    }
}