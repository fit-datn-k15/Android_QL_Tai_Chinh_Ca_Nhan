package com.example.quanlythuchi.view.main.home.income

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentIncomeBinding
import com.example.quanlythuchi.extension.formatDateTime

import com.example.quanlythuchi.view.adapter.AdapterIncome
import com.example.quanlythuchi.view.main.home.category.FragmentCategoryDetail
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding,IncomeViewModel>(),IncomeListener,AdapterIncome.OnClickListener {
    override val viewModel: IncomeViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_income

    val adapter by lazy {  AdapterIncome(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@IncomeFragment
            viewModel = this@IncomeFragment.viewModel
            viewBinding.rcvIncome.adapter = this@IncomeFragment.adapter
        }

        viewModel.isCategorySuccess.observe(viewLifecycleOwner) {
            if (it) {
                adapter.submitList(viewModel.listCategory)
            }
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
                    viewBinding.pickTime.text = viewModel.date.formatDateTime()
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
            findNavController().navigate(
                R.id.frg_category_detail,
                Bundle().apply {
                    putString(
                        FragmentCategoryDetail.KEY_CATEGORY,
                        FragmentCategoryDetail.INCOME
                    )
                }
            )
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
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time
    }
}