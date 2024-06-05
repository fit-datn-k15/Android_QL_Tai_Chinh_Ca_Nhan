package com.example.quanlythuchi.view.edit_expense_income

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.AppBindingAdapter.setTimeFormatter
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentEditBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.extension.toLocalDate
import com.example.quanlythuchi.view.adapter.AdapterCategory
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class FragmentEditExpenseIncome : BaseFragment<FragmentEditBinding, EditExpenseIncomeViewModel>(), EditExpenseIncomeListener, AdapterCategory.OnClickListener{
    override val viewModel: EditExpenseIncomeViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_edit
    private val adapter by lazy { AdapterCategory(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.itemData = arguments?.getParcelable(Constant.KEY_ITEM_IE, ExpenseIncome::class.java)
        viewModel.listCategory = arguments?.getParcelableArray(Constant.KEY_LIST_CATEGORY, Category::class.java)?.toList()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@FragmentEditExpenseIncome
            viewModel = this@FragmentEditExpenseIncome.viewModel
        }
        if (viewModel.itemData == null || viewModel.listCategory == null) {
            onClickBack()
        }
        viewModel.apply {
            note = itemData?.noteExpenseIncome ?: ""
            date = itemData?.date.toLocalDate()
            money = itemData?.money?.toString() ?: ""
        }
        viewBinding.rcv.adapter = this.adapter
        setTime()
    }

    override fun onClickBack() {
        findNavController().popBackStack()
    }

    override fun onClickUpdate() {

    }

    override fun openDayPicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                viewModel.apply {
                    date = LocalDate.of(year, month+1, dayOfMonth)
                }
                viewBinding.pickTime.setTimeFormatter(viewModel.date)
            },
            viewModel.date.year,
            viewModel.date.monthValue -1,
            viewModel.date.dayOfMonth
        )
        picker.show()
    }
    private fun setTime() {
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time
    }
    companion object {
        const val UPDATE_EXPENSE = 0
        const val UPDATE_INCOME = 1
    }

    override fun onClick(position: Int, listCategory: MutableList<Category>) {

    }
}