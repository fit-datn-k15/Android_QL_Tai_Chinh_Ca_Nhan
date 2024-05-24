package com.example.quanlythuchi.view.main.calendar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.DayOfWeekHeaderBinding
import com.example.quanlythuchi.databinding.Example5FragmentBinding
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
@AndroidEntryPoint
class CalendarFragment : BaseFragment<Example5FragmentBinding, CalendarViewModel>(),
    CalendarListener, AdapterExpenseIncomeReport.OnClickListener, MondayView.OnClickDayListener {

    override val viewModel: CalendarViewModel by viewModels()
    override val layoutID: Int = R.layout.example_5_fragment


    private val adapter by lazy { AdapterExpenseIncomeReport(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDataByDate()
        viewBinding.listener = this
        viewBinding.listIncomeAndExpense.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = this@CalendarFragment.adapter
        }

        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)
        configureBinders(daysOfWeek)
        viewBinding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        viewBinding.calendarView.scrollToMonth(currentMonth)

        viewBinding.calendarView.monthScrollListener = { month ->
            viewBinding.monthYearText.text = month.yearMonth.displayText()
            viewModel.selectedDate?.let {
                viewModel.selectedDate = null
                viewBinding.calendarView.notifyDateChanged(it)
            }
        }
        viewModel.isGetDataByMonth.observe(viewLifecycleOwner) {
            viewBinding.calendarView.notifyCalendarChanged()
        }
    }


    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {

        viewBinding.calendarView.dayBinder = MondayView(viewModel = this@CalendarFragment.viewModel,viewLifecycleOwner, this)

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val listDayOfWeek = DayOfWeekHeaderBinding.bind(view).listDayOfWeek.root
        }

        viewBinding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.listDayOfWeek.tag == null) {
                        container.listDayOfWeek.tag = data.yearMonth
                        container.listDayOfWeek.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].displayText(uppercase = true)
                            }
                    }
                }
            }
    }

    override fun onClickDay(selectedDate: LocalDate, oldDate : LocalDate?) {
        val binding = this@CalendarFragment.viewBinding
        binding.calendarView.notifyDateChanged(selectedDate)
        oldDate?.let { binding.calendarView.notifyDateChanged(it) }
    }

    override fun exFiveNextMonthImage() {
        viewBinding.calendarView.findFirstVisibleMonth()?.let {
            viewBinding.calendarView.smoothScrollToMonth(it.yearMonth.nextMonth)
        }
    }

    override fun exFivePreviousMonthImage() {
        viewBinding.calendarView.findFirstVisibleMonth()?.let {
            viewBinding.calendarView.smoothScrollToMonth(it.yearMonth.previousMonth)
        }
    }

    override fun onClick(item: ExpenseIncome) {

    }
}
