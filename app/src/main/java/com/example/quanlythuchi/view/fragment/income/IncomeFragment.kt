package com.example.quanlythuchi.view.fragment.income

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentIncomeBinding

import com.example.quanlythuchi.view.adapter.AdapterIncome

class IncomeFragment : BaseFragment<FragmentIncomeBinding,IncomeViewModel>(),IncomeListener {
    override val viewModel: IncomeViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_income
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@IncomeFragment
        }

        viewModel.getCategory(requireContext())
        viewModel.categorys.observe(viewLifecycleOwner) {
            val adapter = AdapterIncome()
            adapter.submitList(it)
            viewBinding.rcvIncome.adapter = adapter
        }
        openDayPicker()
    }

    override fun openDayPicker() {

    }
}