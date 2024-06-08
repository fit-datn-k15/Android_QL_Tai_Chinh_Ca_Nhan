package com.example.quanlythuchi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.data.entity.BaseDataEI
import com.example.quanlythuchi.data.entity.TotalCategory
import com.example.quanlythuchi.databinding.ItemTotalCalendarBinding
import com.example.quanlythuchi.databinding.ItemTotalCategoryBinding
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome


class AdapterTotalCategory(private var onClickListener: OnClickListener) :
    ListAdapter<TotalCategory, AdapterTotalCategory.CategoryViewHolder>(Callback()) {
    class CategoryViewHolder(var viewBinding: ItemTotalCategoryBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: TotalCategory) {
            viewBinding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = DataBindingUtil.inflate<ItemTotalCategoryBinding>(
            inflater, R.layout.item_total_category, parent, false
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

    class Callback : DiffUtil.ItemCallback<TotalCategory>() {
        override fun areItemsTheSame(oldItem: TotalCategory, newItem: TotalCategory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TotalCategory, newItem: TotalCategory): Boolean {
            return oldItem == newItem
        }

    }
    interface OnClickListener {
        fun onClickItemEI(item : TotalCategory)
    }
}