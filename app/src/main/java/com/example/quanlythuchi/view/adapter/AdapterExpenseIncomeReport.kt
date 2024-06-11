package com.example.quanlythuchi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.databinding.ItemTotalCalendarBinding
import com.example.quanlythuchi.view.main.calendar.FinancialRecord


class AdapterExpenseIncomeReport(private var onClickListener: OnClickListener) :
    ListAdapter<FinancialRecord, AdapterExpenseIncomeReport.CategoryViewHolder>(Callback()) {
    class CategoryViewHolder(var viewBinding: ItemTotalCalendarBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: FinancialRecord) {
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

    class Callback : DiffUtil.ItemCallback<FinancialRecord>() {
        override fun areItemsTheSame(oldItem: FinancialRecord, newItem: FinancialRecord): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FinancialRecord, newItem: FinancialRecord): Boolean {
            return oldItem == newItem
        }

    }
    interface OnClickListener {
        fun onClickItemEI(item : FinancialRecord)
    }
}