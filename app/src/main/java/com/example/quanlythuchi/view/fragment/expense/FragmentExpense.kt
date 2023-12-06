package com.example.quanlythuchi.view.fragment.expense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.room.AppDatabase
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.databinding.FragmentExpenseBinding

import com.example.quanlythuchi.view.adapter.AdapterExpense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.m
import java.util.Calendar

class FragmentExpense : BaseFragment<FragmentExpenseBinding,ExpenseViewModel>(), ExpenseListener {
    override val viewModel: ExpenseViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_expense
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@FragmentExpense
        }
        viewModel.getCategory(requireContext())
        val adapter= AdapterExpense()
        viewModel.categorys.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewBinding.rcvExpense.adapter = adapter
        }
        setTimeDefault()
        adapter.setOnClickListener(object : AdapterExpense.OnClickListener{
            override fun onClick(position: Int, c: Category) {
                Toast.makeText(requireContext(),"..",Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun openDayPicker() {
        val picker = DatePickerDialog(
            requireContext(), { view, year, month, dayOfMonth ->
                viewModel.day = dayOfMonth
                viewModel.month = month
                viewModel.year = year
                var d = String.format(Constance.DATE_FORMAT, viewModel.day)
                var m = String.format(Constance.DATE_FORMAT, viewModel.month + 1)
                var time = "${d}/${m}/${viewModel.year}"
                viewBinding.pickTime.text = time
            }, viewModel.year, viewModel.month, viewModel.day)
        picker.show()

    }
    fun setTimeDefault() {
        var d = String.format(Constance.DATE_FORMAT, viewModel.day)
        var m = String.format(Constance.DATE_FORMAT, viewModel.month + 1)
        var time = "${d}/${m}/${viewModel.year}"
        viewBinding.pickTime.text = time
    }


}