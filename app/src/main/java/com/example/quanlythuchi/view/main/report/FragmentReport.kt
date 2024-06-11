package com.example.quanlythuchi.view.main.report

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.data.entity.FinancialSummaryWithCategory
import com.example.quanlythuchi.databinding.FagmentReportBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.extension.formatMonthVN
import com.example.quanlythuchi.extension.navigateWithAnim
import com.example.quanlythuchi.view.adapter.AdapterTotalCategory
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
            vpg.adapter = AdapterReport(this@FragmentReport)
            TabLayoutMediator(tabLayoutReport, vpg) { tab, position ->
                if (position == 0)
                    tab.text = context?.getString(R.string.expense)
                else
                    tab.text = context?.getString(R.string.income)
            }.attach()
        }
        viewBinding.vpg.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {

                    viewModel.isFragment = FRAGMENT_EXPENSE
                }
                else {

                    viewModel.isFragment = FRAGMENT_INCOME
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }
        })

        viewBinding.apply {
            // adapterRcv.submitList(viewModel.dataExpenseRcv.value)
            rcv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rcv.adapter = this@FragmentReport.adapterRcv
        }
        viewModel.dataRcv.observe(viewLifecycleOwner) {
            adapterRcv.submitList(it)
        }
        viewBinding.rcv.isNestedScrollingEnabled = false;
        val itemDeclaration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        viewBinding.rcv.addItemDecoration(itemDeclaration)

        viewBinding.monthSelected.text = "Tháng " + YearMonth.from(viewModel.date).formatMonthVN()
    }
    override fun openDayPicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                viewModel.apply {
                    date = LocalDate.of(year, month+1, dayOfMonth)
                }
                if (viewModel.isFragment == FRAGMENT_EXPENSE)
                    viewModel.filterDataExpenseByMonth(YearMonth.from(viewModel.date))
                else
                    viewModel.filterDataIncomeByMonth(YearMonth.from(viewModel.date))
                viewBinding.monthSelected.text = "Tháng " + YearMonth.from(viewModel.date).formatMonthVN()

            },
            viewModel.date.year,
            viewModel.date.monthValue -1,
            viewModel.date.dayOfMonth
        )
        picker.show()
    }
    companion object {
        const val  FRAGMENT_EXPENSE = 0
        const val FRAGMENT_INCOME = 1
    }
    override fun btnBackDay() {

    }

    override fun openViewAll() {
        findNavController().navigateWithAnim(R.id.frg_all_income_expense, bundleOf())
    }

    override fun btnNextDay() {

    }

    private fun setTimeDefault() {
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time

    }

    override fun onClickItemEI(item: FinancialSummaryWithCategory) {
        findNavController().navigateWithAnim(R.id.frg_list_data, bundleOf(
            Constant.KEY_ITEM_CATEGORY_OF_DATA to item.category.idCategory,
            Constant.DATA to item.listRecord,
            Constant.TITLE_CATEGORY to item.category.title
        ))
    }
}