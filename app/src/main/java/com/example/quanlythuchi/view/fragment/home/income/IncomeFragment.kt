package com.example.quanlythuchi.view.fragment.home.income

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.databinding.FragmentIncomeBinding
import com.example.quanlythuchi.extension.formatDateTime

import com.example.quanlythuchi.view.adapter.AdapterIncome
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding,IncomeViewModel>(),IncomeListener,AdapterIncome.OnClickListener {
    override val viewModel: IncomeViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_income
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@IncomeFragment
            viewModel = this.viewModel
        }

        viewModel.isCategorySuccess.observe(viewLifecycleOwner) {
            val adapter = AdapterIncome(this)
            adapter.submitList(viewModel.listCategory)
            viewBinding.rcvIncome.adapter = adapter
        }
        viewModel.isAddIncome.observe(viewLifecycleOwner) {
            if(it) {
                Toast.makeText(this@IncomeFragment.requireContext(),"Đã thêm khoản thu", Toast.LENGTH_SHORT).show()
            }
        }
        setTimeDefault()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategory()
    }

    override fun openDayPicker() {
            val picker = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    viewModel.apply {
                        date = LocalDate.of(year, month+1, dayOfMonth)
                    }
                    viewBinding.pickTime.text = formatDateTime(viewModel.date)
                },
                viewModel.date.year,
                viewModel.date.monthValue -1,
                viewModel.date.dayOfMonth
            )
            picker.show()

    }

    override fun submitIncome() {
        viewModel.submitIncome();
    }

    override fun onClick(position: Int, listCategory: MutableList<Category>) {
        if(position == listCategory.size -1 ) {

        }
        else {
            if (viewModel.idItemRcvCategorySelect != -1) {
                viewBinding.rcvIncome
                    .findViewHolderForAdapterPosition(viewModel.idItemRcvCategorySelect)!!
                    .itemView.isSelected = false
            }
            viewModel.idItemRcvCategorySelect = position
            viewBinding.rcvIncome
                .findViewHolderForAdapterPosition(viewModel.idItemRcvCategorySelect)!!
                .itemView.isSelected = true
            viewModel.category = listCategory[position]
        }
    }
    private fun setTimeDefault() {
        val time = formatDateTime(viewModel.date)
        viewBinding.pickTime.text = time
    }


}