package com.example.quanlythuchi.view.main.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.databinding.ItemTotalCalendarBinding


class AdapterExpenseIncomeReport(private var onClickListener: OnClickListener) :
    ListAdapter<ExpenseIncome, AdapterExpenseIncomeReport.CategoryViewHolder>(Callback()) {
    class CategoryViewHolder(var viewBinding: ItemTotalCalendarBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: ExpenseIncome) {
            viewBinding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = DataBindingUtil.inflate<ItemTotalCalendarBinding>(
            inflater, R.layout.item_total_calendar, parent, false
        )
        return CategoryViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClickItemEI(getItem(holder.absoluteAdapterPosition))
        }
    }

    class Callback : DiffUtil.ItemCallback<ExpenseIncome>() {
        override fun areItemsTheSame(oldItem: ExpenseIncome, newItem: ExpenseIncome): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExpenseIncome, newItem: ExpenseIncome): Boolean {
            return oldItem == newItem
        }

    }
    interface OnClickListener {
        fun onClickItemEI(item : ExpenseIncome)
    }
}