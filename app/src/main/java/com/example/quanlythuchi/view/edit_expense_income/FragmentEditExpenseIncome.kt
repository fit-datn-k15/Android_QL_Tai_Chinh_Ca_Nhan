package com.example.quanlythuchi.view.edit_expense_income

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.AppBindingAdapter.setTimeFormatter
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentEditBinding
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.extension.toLocalDate
import com.example.quanlythuchi.view.adapter.AdapterCategory
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import com.example.quanlythuchi.view.main.home.category.FragmentCategoryDetail
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class FragmentEditExpenseIncome : BaseFragment<FragmentEditBinding, EditExpenseIncomeViewModel>(), EditExpenseIncomeListener, AdapterCategory.OnClickListener{
    override val viewModel: EditExpenseIncomeViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_edit
    private val adapter by lazy { AdapterCategory(this)}
    private var x : List<Category>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            viewModel.itemData = arguments?.getParcelable(Constant.KEY_ITEM_IE)
            viewModel.listCategory = arguments?.getParcelableArrayList(Constant.KEY_LIST_CATEGORY)
        }
        catch (e : Exception) {
            e.message?.let { Log.e("FragmentEditExpenseIncome", "argument error $it") }
        }
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
            /*
            check xem khooản thu (chi) thuộc category nào, sau đó gán  vị trí [i] category đó vào itemCategorySelected
             */
            if (listCategory != null) {
                for (item in listCategory!!) {
                    if(item.idCategory == itemData?.idCategory) {
                        itemCategorySelected = listCategory!!.indexOf(item)
                        categorySelected = item
                        break
                    }
                }
            }
        }
        setData()
        viewModel.isUpdate.observe(viewLifecycleOwner) {
            if (it) {

              //  showToast()
                viewModel.isUpdate.value = false
            }
        }
    }
   private  fun showToast(message : String) {
       Toast.makeText(
           requireContext(),
           getString(R.string.message_update_income_expense) + getString(R.string.income),
           Toast.LENGTH_SHORT
       ).show()
   }

    override fun onClickBack() {
        findNavController().popBackStack()
    }

    override fun onClickUpdate() {
        viewModel.itemData?.let {
            viewModel.updateItemData(it.typeExpenseOrIncome)
        }
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
    private fun setData() {
        val time = viewModel.date.formatDateTime()
        viewBinding.pickTime.text = time

        if (viewModel.itemData?.typeExpenseOrIncome == ExpenseIncome.TYPE_EXPENSE) {
            viewBinding.typeUpdate.text = getString(R.string.update_expense)
        }
        else {
            viewBinding.typeUpdate.text = getString(R.string.update_income)
        }
        adapter.submitList(viewModel.listCategory)
        viewBinding.rcv.adapter = this.adapter
       viewBinding.rcv.post { viewBinding.rcv
           .findViewHolderForAdapterPosition(viewModel.itemCategorySelected)!!
           .itemView.isSelected = true }
    }

    override fun onClickItemCategory(position: Int, listCategory: MutableList<Category>) {
        if (viewModel.itemCategorySelected != -1) {
            viewBinding.rcv
                .findViewHolderForAdapterPosition(viewModel.itemCategorySelected)!!
                .itemView.isSelected = false
        }
        viewModel.itemCategorySelected = position
        viewBinding.rcv
            .findViewHolderForAdapterPosition(viewModel.itemCategorySelected)!!
            .itemView.isSelected = true
        viewModel.categorySelected = listCategory[position]
    }

}