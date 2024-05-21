package com.example.quanlythuchi.view.main.home.expense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.AppBindingAdapter.setTimeFormatter
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentExpenseBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.view.adapter.AdapterExpense
import com.example.quanlythuchi.view.main.home.ShareHomeViewModel
import com.example.quanlythuchi.view.main.home.category.FragmentCategoryDetail
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class FragmentExpense : BaseFragment<FragmentExpenseBinding,ShareHomeViewModel>(), ExpenseListener,AdapterExpense.OnClickListener {
    override val viewModel: ShareHomeViewModel by activityViewModels()
    override val layoutID: Int = R.layout.fragment_expense
    val adapter by lazy {   AdapterExpense(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategoryExpense()
        viewBinding.apply {
            listener = this@FragmentExpense
            viewModel = this@FragmentExpense.viewModel
            viewBinding.rcvExpense.adapter = this@FragmentExpense.adapter
        }

        viewModel.listCategoryExpense.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        setTimeDefault()

        viewModel.isAddExpense.observe(viewLifecycleOwner) {
            if (it) {
                clearDataInput()
                Toast.makeText(this@FragmentExpense.requireContext(), "Đã thêm khoản chi", Toast.LENGTH_SHORT).show()
                viewModel.isAddExpense.postValue(false)
            }
        }
    }

    override fun openDayPicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                viewModel.apply {
                    dateExpense = LocalDate.of(year, month+1, dayOfMonth)
                }
                viewBinding.pickTime.setTimeFormatter(viewModel.dateExpense)
            },
            viewModel.dateExpense.year,
            viewModel.dateExpense.monthValue -1,
            viewModel.dateExpense.dayOfMonth
        )
        picker.show()

    }

    override fun submitExpense() {
        viewModel.submitExpense()
    }

    private fun setTimeDefault() {
        val time = viewModel.dateExpense.formatDateTime();
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
            if (viewModel.itemCategoryExpenseSelected != -1) {
                viewBinding.rcvExpense
                    .findViewHolderForAdapterPosition(viewModel.itemCategoryExpenseSelected)!!
                    .itemView.isSelected = false
            }
            viewModel.itemCategoryExpenseSelected = position
            viewBinding.rcvExpense
                .findViewHolderForAdapterPosition(viewModel.itemCategoryExpenseSelected)!!
                .itemView.isSelected = true
            viewModel.categoryExpenseSelected = listCategory[position]
        }
    }
    private fun clearDataInput() {
        viewBinding.edtNoteExpense.setText("")
        viewBinding.inputMoneyExpense.setText("")
    }

}