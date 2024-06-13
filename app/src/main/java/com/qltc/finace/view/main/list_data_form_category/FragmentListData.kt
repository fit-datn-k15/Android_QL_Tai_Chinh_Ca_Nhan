package com.qltc.finace.view.main.list_data_form_category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qltc.finace.R
import com.qltc.finace.base.BaseFragment
import com.qltc.finace.base.Constant
import com.qltc.finace.databinding.FragmentListDataFromCategoryBinding
import com.qltc.finace.view.adapter.AdapterExpenseIncomeReport
import com.qltc.finace.view.main.calendar.FinancialRecord
import com.qltc.finace.view.main.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListData : BaseFragment<FragmentListDataFromCategoryBinding, ReportViewModel>(),
    ListDataListener, AdapterExpenseIncomeReport.OnClickListener {

    override val viewModel: ReportViewModel  by activityViewModels()
    override val layoutID: Int = R.layout.fragment_list_data_from_category
    private val adapter by lazy { AdapterExpenseIncomeReport(this) }
    private var idCategory = ""
    private var titleCategory = ""
    private var data : List<FinancialRecord> = listOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            rcv.adapter = this@FragmentListData.adapter
            rcv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            listener = this@FragmentListData
        }
        try {
            titleCategory = arguments?.getString(Constant.TITLE_CATEGORY) ?: ""
            idCategory = arguments?.getString(Constant.KEY_ITEM_CATEGORY_OF_DATA) ?: ""
            data = (arguments?.getParcelableArrayList<FinancialRecord>(Constant.DATA)?.toList()) ?: listOf()
        }
        catch (e : Exception) {
            e.message?.let { Log.e("FragmentEditExpenseIncome", "argument error $it") }
        }
        if (idCategory.isEmpty()) {
            findNavController().popBackStack()
        }

        viewBinding.title.text = titleCategory
        adapter.submitList(data)
    }
    override fun onClickItemEI(item: FinancialRecord) {

    }

    override fun onClickBack() {
        findNavController().popBackStack()
    }

}