package com.example.quanlythuchi.view.main.calendar

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.TAG
import com.example.quanlythuchi.databinding.ItemDayViewCalendarBinding
import com.example.quanlythuchi.extension.getColorCompat
import com.example.quanlythuchi.extension.setTextColorRes
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate

class MondayView(val viewModel: CalendarViewModel,
                 val owner: LifecycleOwner,
                 val onClickDay : OnClickDayListener,
) : MonthDayBinder<MondayView.ItemDayViewCalendar> {
    override fun create(view: View): ItemDayViewCalendar {
        return ItemDayViewCalendar(view,owner)
    }

    override fun bind(container: ItemDayViewCalendar, data: CalendarDay) {
            container.day = data
            container.setUpView()
    }
    inner class ItemDayViewCalendar(view : View, lifecycleOwner: LifecycleOwner) : ViewContainer(view) {
        val binding = ItemDayViewCalendarBinding.bind(view)
        lateinit var day: CalendarDay // Will be set when this container is bound.
        init {
            view.setOnClickListener {
                if (day.position == DayPosition.MonthDate) {
                    if (viewModel.selectedDate != day.date) {
                        val oldDate = viewModel.selectedDate
                        viewModel.selectedDate = day.date
                        onClickDay.onClickDay(day.date, oldDate)
                    }
                }
            }
            binding.lifecycleOwner = lifecycleOwner
            viewModel.isGetDataByMonth.observe(lifecycleOwner) {
                if(it) {
                    Log.i(TAG, "isGetData: $it")
                }
            }
        }
        fun setUpView() {
            val context = binding.root.context
            binding.apply {
                textViewDay.text = day.date.dayOfMonth.toString()
                itemTopIncomeLine.background = null
                itemBottomExpenseLine.background = null
                if (day.position == DayPosition.MonthDate) {
                    textViewDay.setTextColorRes(R.color.black70)
                    frameLayoutItemDayView.setBackgroundColor(context.getColorCompat(R.color.day_selected_EFF4F9))
                    layoutItemDayView.setBackgroundResource(if (viewModel.selectedDate == day.date) R.drawable.example_5_selected_bg else 0)

                    if(viewModel.listGroupExpenseToShowDayView[day.date] != null) {
                        itemBottomExpenseLine.setBackgroundColor(context.getColor(R.color.red_F74040))
                    }
                    if (viewModel.listGroupIncomeToShowDayView[day.date] != null) {
                        itemTopIncomeLine.setBackgroundColor(context.getColor(R.color.green_700))
                    }
                } else {
                    textViewDay.setTextColorRes(R.color.white)
                    frameLayoutItemDayView.setBackgroundColor(context.getColor(R.color.day_not_selected_EFF4F9))
                }
            }
        }

    }
    public interface OnClickDayListener {
        fun onClickDay(selectedDate: LocalDate, oldDate : LocalDate?)
    }
}
