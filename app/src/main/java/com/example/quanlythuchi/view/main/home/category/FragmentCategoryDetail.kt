package com.example.quanlythuchi.view.main.home.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentCategoryDetailBinding
import com.example.quanlythuchi.view.adapter.AdapterCategoryDetail
import com.example.quanlythuchi.view.adapter.AdapterExpense
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCategoryDetail : BaseFragment<FragmentCategoryDetailBinding, CategoryDetailViewModel>(), CategoryDetailListener, AdapterCategoryDetail.OnClickListener {
    override val viewModel: CategoryDetailViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_category_detail

    val adapter by lazy {  AdapterCategoryDetail(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@FragmentCategoryDetail
            viewModel = this@FragmentCategoryDetail.viewModel
        }
        viewModel.getCategory()
        viewModel.isCategorySuccess.observe(viewLifecycleOwner) {
            if(it) {
                adapter.submitList(viewModel.listCategory)
            }
        }
        viewBinding.rcv.adapter = this.adapter
        if (arguments != null && arguments?.getString(KEY_CATEGORY) == INCOME )
            viewModel.nameCategory = this.getString(R.string.income)
        else
            viewModel.nameCategory = this.getString(R.string.expense)
    }


    override fun onClick(position: Int, listCategory: MutableList<Category>) {
    }

    override fun backPress() {
        findNavController().popBackStack()
    }
    companion object {
        const val KEY_CATEGORY = "KEY_CATEGORY"
        const val INCOME = "INCOME"
        const val EXPENSE= "EXPENSE"
    }
}