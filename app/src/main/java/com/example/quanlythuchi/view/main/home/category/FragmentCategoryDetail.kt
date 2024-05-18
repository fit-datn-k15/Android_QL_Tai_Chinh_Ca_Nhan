package com.example.quanlythuchi.view.main.home.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.TAG
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.databinding.FragmentCategoryDetailBinding
import com.example.quanlythuchi.extension.updateList
import com.example.quanlythuchi.view.adapter.AdapterCategoryDetail
import com.example.quanlythuchi.view.adapter.AdapterExpense
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class FragmentCategoryDetail :
    BaseFragment<FragmentCategoryDetailBinding, CategoryDetailViewModel>(),
    CategoryDetailListener, AdapterCategoryDetail.OnClickListener, OnSwipeItemCategoryDetail {
    override val viewModel: CategoryDetailViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_category_detail

    val adapter by lazy { AdapterCategoryDetail(this) }
    private var typeCategory: String = Fb.CategoryIncome

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@FragmentCategoryDetail
            viewModel = this@FragmentCategoryDetail.viewModel
        }

        typeCategory = arguments?.getString(KEY_CATEGORY) ?: Fb.CategoryIncome
        if (typeCategory == Fb.CategoryIncome) {
            viewModel.nameCategory = this.getString(R.string.income)
        } else
            viewModel.nameCategory = this.getString(R.string.expense)

        viewModel.getCategory(typeCategory = typeCategory)
        viewBinding.rcv.adapter = this.adapter
        val itemDeclaration = DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL)
        viewBinding.rcv.addItemDecoration(itemDeclaration)
        val itemTouchHelperSimpleCallback = OnSwipeAdapterCategoryDetail(0,ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperSimpleCallback).attachToRecyclerView(viewBinding.rcv)

        viewModel.listCategory.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }


    override fun onClick(position: Int, listCategory: MutableList<Category>) {
    }

    override fun backPress() {
        findNavController().popBackStack()
    }
    override fun addCategory() {
        findNavController().navigate(R.id.frg_add_category,
            Bundle().apply {
                putString(KEY_CATEGORY, typeCategory)
            })
    }

    companion object {
        const val KEY_CATEGORY = "KEY_CATEGORY"
    }

    override fun onSwipe(viewHolder: AdapterCategoryDetail.CategoryDetailViewHolder) {

    }
}