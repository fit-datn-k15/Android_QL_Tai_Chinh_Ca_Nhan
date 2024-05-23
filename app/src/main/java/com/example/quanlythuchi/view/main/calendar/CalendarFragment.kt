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
import com.example.quanlythuchi.databinding.ItemDayViewCalendarBinding
import com.example.quanlythuchi.extension.getColorCompat
import com.example.quanlythuchi.extension.setTextColorRes
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
@AndroidEntryPoint
class CalendarFragment : BaseFragment<Example5FragmentBinding, CalendarViewModel>(), CalendarListener, AdapterExpenseIncomeReport.OnClickListener {

    override val viewModel: CalendarViewModel by viewModels()
    override val layoutID: Int = R.layout.example_5_fragment
    private var selectedDate: LocalDate? = null


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
            viewBinding.exFiveMonthYearText.text = month.yearMonth.displayText()

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                viewBinding.calendarView.notifyDateChanged(it)
               // updateAdapterForDate(null)
            }
        }
    }


    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class ItemDayViewCalendar(val binding: ItemDayViewCalendarBinding) : ViewContainer(binding.root) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            val binding = this@CalendarFragment.viewBinding
                            binding.calendarView.notifyDateChanged(day.date)
                            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
                           // updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }
        viewBinding.calendarView.dayBinder = object : MonthDayBinder<ItemDayViewCalendar> {
            override fun create(view: View) = ItemDayViewCalendar(ItemDayViewCalendarBinding.inflate(layoutInflater))
            override fun bind(container: ItemDayViewCalendar, data: CalendarDay) {
                container.day = data
                val context = container.binding.root.context
                container.binding.textViewDay.text = data.date.dayOfMonth.toString()
                  // sửa từ parent xuống frame
                container.binding.apply {
                    itemTopIncomeLine.background = null
                    itemBottomExpenseLine.background = null

                    if (data.position == DayPosition.MonthDate) {
                        textViewDay.setTextColorRes(R.color.black70)
                        frameLayoutItemDayView.setBackgroundColor(context.getColorCompat(R.color.day_selected_EFF4F9))
                        layoutItemDayView.setBackgroundResource(if (selectedDate == data.date) R.drawable.example_5_selected_bg else 0)

                        if(viewModel.listGroupExpense[data.date] != null) {
                            itemBottomExpenseLine.setBackgroundColor(context.getColor(R.color.red_F74040))
                        }
                        if (viewModel.listGroupIncome[data.date] != null) {
                            itemTopIncomeLine.setBackgroundColor(context.getColor(R.color.green_700))
                        }
                    } else {
                        textViewDay.setTextColorRes(R.color.white)
                        frameLayoutItemDayView.setBackgroundColor(context.getColor(R.color.day_not_selected_EFF4F9))
                    }
                }
            }
        }

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
