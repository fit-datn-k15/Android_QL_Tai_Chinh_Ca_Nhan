package com.example.quanlythuchi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.data.entity.FinancialSummaryWithCategory
import com.example.quanlythuchi.databinding.ItemTotalCategoryBinding


class AdapterTotalCategory(private var onClickListener: OnClickListener) :
    ListAdapter<FinancialSummaryWithCategory, AdapterTotalCategory.CategoryViewHolder>(Callback()) {
    class CategoryViewHolder(var viewBinding: ItemTotalCategoryBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: FinancialSummaryWithCategory) {
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

    class Callback : DiffUtil.ItemCallback<FinancialSummaryWithCategory>() {
        override fun areItemsTheSame(oldItem: FinancialSummaryWithCategory, newItem: FinancialSummaryWithCategory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FinancialSummaryWithCategory, newItem: FinancialSummaryWithCategory): Boolean {
            return oldItem == newItem
        }

    }
    interface OnClickListener {
        fun onClickItemEI(item : FinancialSummaryWithCategory)
    }
}