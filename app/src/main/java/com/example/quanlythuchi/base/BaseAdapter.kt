package com.example.quanlythuchi.base



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item: Any>(
    private var onItemClick : OnItemClickListener<Item>?= null,
    callBack: DiffUtil.ItemCallback<Item>,
    val layoutId : Int
) : ListAdapter<Item, BaseViewHolder<ViewDataBinding>>(callBack)  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewDataBinding> {
        return BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }
}


interface OnItemClickListener<Item : Any> {
    fun onItemClick(item: Item)
}
open class BaseViewHolder<VBD : ViewDataBinding>(
    private val binding: VBD
) : RecyclerView.ViewHolder(binding.root)