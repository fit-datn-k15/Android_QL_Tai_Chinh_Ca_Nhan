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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class Example5Fragment : BaseFragment<Example5FragmentBinding, CalendarViewModel>(), CalendarListener {

    override val viewModel: CalendarViewModel by viewModels()
    override val layoutID: Int = R.layout.example_5_fragment
    private var selectedDate: LocalDate? = null

    private val flightsAdapter = Example5FlightsAdapter()
    private val flights = generateFlights().groupBy { it.time.toLocalDate() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  binding = Example5FragmentBinding.bind(view)
        viewBinding.listener = this
        viewBinding.exFiveRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = flightsAdapter
        }
        flightsAdapter.notifyDataSetChanged()

        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)
        configureBinders(daysOfWeek)
        viewBinding.exFiveCalendar.setup(startMonth, endMonth, daysOfWeek.first())
        viewBinding.exFiveCalendar.scrollToMonth(currentMonth)

        viewBinding.exFiveCalendar.monthScrollListener = { month ->
            viewBinding.exFiveMonthYearText.text = month.yearMonth.displayText()

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                viewBinding.exFiveCalendar.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        flightsAdapter.flights.clear()
        flightsAdapter.flights.addAll(flights[date].orEmpty())
        flightsAdapter.notifyDataSetChanged()
    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class ItemDayViewCalendar(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = ItemDayViewCalendarBinding.bind(view)
            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            val binding = this@Example5Fragment.viewBinding
                            binding.exFiveCalendar.notifyDateChanged(day.date)
                            oldDate?.let { binding.exFiveCalendar.notifyDateChanged(it) }
                            updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }
        viewBinding.exFiveCalendar.dayBinder = object : MonthDayBinder<ItemDayViewCalendar> {
            override fun create(view: View) = ItemDayViewCalendar(view)
            override fun bind(container: ItemDayViewCalendar, data: CalendarDay) {
                container.day = data
                val context = container.binding.root.context
                val textView = container.binding.exFiveDayText
                val layout = container.binding.exFiveDayLayout  // sửa từ parent xuống frame
                textView.text = data.date.dayOfMonth.toString()

                val flightTopView = container.binding.exFiveDayFlightTop
                val flightBottomView = container.binding.exFiveDayFlightBottom
                flightTopView.background = null
                flightBottomView.background = null

                if (data.position == DayPosition.MonthDate) {
                    textView.setTextColorRes(R.color.black70)
                    layout.setBackgroundResource(if (selectedDate == data.date) R.drawable.example_5_selected_bg else 0)
                    val flights = flights[data.date]
                    if (flights != null) {
                        if (flights.count() == 1) {
                            flightBottomView.setBackgroundColor(context.getColorCompat(flights[0].color))
                        } else {
                            flightTopView.setBackgroundColor(context.getColorCompat(flights[0].color))
                            flightBottomView.setBackgroundColor(context.getColorCompat(flights[1].color))
                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.white)
                    container.binding.frame.setBackgroundColor(context.getColor(R.color.day_not_selected_EFF4F9))
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = DayOfWeekHeaderBinding.bind(view).legendLayout.root
        }

        viewBinding.exFiveCalendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = data.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].displayText(uppercase = true)
                            }
                    }
                }
            }
    }

    override fun exFiveNextMonthImage() {
        viewBinding.exFiveCalendar.findFirstVisibleMonth()?.let {
            viewBinding.exFiveCalendar.smoothScrollToMonth(it.yearMonth.nextMonth)
        }
    }

    override fun exFivePreviousMonthImage() {
        viewBinding.exFiveCalendar.findFirstVisibleMonth()?.let {
            viewBinding.exFiveCalendar.smoothScrollToMonth(it.yearMonth.previousMonth)
        }
    }
}
