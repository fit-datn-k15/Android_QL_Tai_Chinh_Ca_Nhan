package com.example.quanlythuchi.view.main.home.expense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentExpenseBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.view.adapter.AdapterExpense
import com.example.quanlythuchi.view.main.home.category.FragmentCategoryDetail
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class FragmentExpense : BaseFragment<FragmentExpenseBinding,ExpenseViewModel>(), ExpenseListener,AdapterExpense.OnClickListener {
    override val viewModel: ExpenseViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_expense
    val adapter by lazy {   AdapterExpense(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategory()
        viewBinding.apply {
            listener = this@FragmentExpense
            viewModel = this@FragmentExpense.viewModel
            viewBinding.rcvExpense.adapter = this@FragmentExpense.adapter
        }

        viewModel.listCategory.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        setTimeDefault()

        viewModel.isAddExpense.observe(viewLifecycleOwner) {
            if(it) {
               Toast.makeText(this@FragmentExpense.requireContext(),"Đã thêm khoản chi",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun openDayPicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                viewModel.apply {
                    date = LocalDate.of(year, month+1, dayOfMonth)
                }
                viewBinding.pickTime.text = viewModel.date.formatDateTime()
            },
            viewModel.date.year,
            viewModel.date.monthValue -1,
            viewModel.date.dayOfMonth
        )
        picker.show()

    }

    override fun submitExpense() {
        viewModel.submitExpense()
    }

    private fun setTimeDefault() {
        val time = viewModel.date.formatDateTime();
        viewBinding.pickTime.text = time
    }

    override fun onClick(position: Int, listCategory: MutableList<Category>) {
        if(listCategory[position].idCategory == Fb.ItemAddedCategory) {
            findNavController().navigate(
                R.id.frg_category_detail,
                Bundle().apply {
                    putString(
                        FragmentCategoryDetail.KEY_CATEGORY,
                        Fb.CategoryExpense
                    )
                }
            )
        }
        else {
            if (viewModel.idItemRcvCategorySelect != -1) {
                viewBinding.rcvExpense
                    .findViewHolderForAdapterPosition(viewModel.idItemRcvCategorySelect)!!
                    .itemView.isSelected = false
            }
            viewModel.idItemRcvCategorySelect = position
            viewBinding.rcvExpense
                .findViewHolderForAdapterPosition(viewModel.idItemRcvCategorySelect)!!
                .itemView.isSelected = true
            viewModel.category = listCategory[position]
        }
    }

}