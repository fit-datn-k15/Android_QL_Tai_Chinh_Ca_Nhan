package com.example.quanlythuchi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.ItemCategoryBinding

class AdapterExpense(private var onClickListener: OnClickListener) :
    ListAdapter<Category, AdapterExpense.CategoryViewHolder>(Callback()) {
    var itemSelect = -1;
    class CategoryViewHolder(val view: ItemCategoryBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Category) {
            view.data = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = DataBindingUtil.inflate<ItemCategoryBinding>(
            inflater,
            R.layout.item_category,
            parent,
            false
        )
        return CategoryViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{
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
        fun onClick(position: Int, listCategory : MutableList<Category>)
    }

}