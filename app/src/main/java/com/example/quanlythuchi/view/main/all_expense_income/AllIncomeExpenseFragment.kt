package com.example.quanlythuchi.view.main.all_expense_income

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentAllDataIncomeExpenseBinding
import com.example.quanlythuchi.view.adapter.AdapterExpenseIncomeReport

import com.example.quanlythuchi.view.main.calendar.FinancialRecord
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllIncomeExpenseFragment : BaseFragment<FragmentAllDataIncomeExpenseBinding,AllIncomeExpenseViewModel>(),AllIncomeExpenseListener
, AdapterExpenseIncomeReport.OnClickListener{
    override val viewModel: AllIncomeExpenseViewModel by activityViewModels()
    override val layoutID: Int = R.layout.fragment_all_data_income_expense
    private val adapter by lazy { AdapterExpenseIncomeReport(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAll()
        viewBinding.apply {
            listener = this@AllIncomeExpenseFragment
            rcv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rcv.adapter = this@AllIncomeExpenseFragment.adapter
        }
        viewModel.dataRcv.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onClickItemEI(item: FinancialRecord) {

    }

    override fun onClickBack() {
        findNavController().popBackStack()
    }

}