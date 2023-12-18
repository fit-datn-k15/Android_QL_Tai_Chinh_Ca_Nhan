package com.example.quanlythuchi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.databinding.ItemCategoryIncomeBinding

class AdapterIncome(private var onClickListener: AdapterIncome.OnClickListener) :
    ListAdapter<Category, AdapterIncome.CategoryViewHolder>(Callback()) {
    class CategoryViewHolder(var view: ItemCategoryIncomeBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: Category) {
            view.data = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = DataBindingUtil.inflate<ItemCategoryIncomeBinding>(
            inflater, R.layout.item_category_income, parent, false
        )
        return CategoryViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position, currentList)
        }
    }

    class Callback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    interface OnClickListener {
        fun onClick(position: Int, listCategory: MutableList<Category>)
    }
}