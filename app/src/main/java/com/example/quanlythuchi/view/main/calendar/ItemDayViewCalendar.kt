package com.example.quanlythuchi.view.main.calendar

import android.view.View
import com.example.quanlythuchi.databinding.ItemDayViewCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.ViewContainer

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