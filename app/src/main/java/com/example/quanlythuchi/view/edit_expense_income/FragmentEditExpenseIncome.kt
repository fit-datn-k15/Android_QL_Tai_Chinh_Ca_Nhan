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
import com.example.quanlythuchi.databinding.FragmentEditBinding
import com.example.quanlythuchi.extension.formatDateTime
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class FragmentEditExpenseIncome : BaseFragment<FragmentEditBinding, EditExpenseIncomeViewModel>(), EditExpenseIncomeListener{
    override val viewModel: EditExpenseIncomeViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_edit
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@FragmentEditExpenseIncome
            viewModel = this@FragmentEditExpenseIncome.viewModel
        }
        setTimeDefault()
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
    private fun setTimeDefault() {
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time
    }
    companion object {
        const val UPDATE_EXPENSE = 0
        const val UPDATE_INCOME = 1
    }
}