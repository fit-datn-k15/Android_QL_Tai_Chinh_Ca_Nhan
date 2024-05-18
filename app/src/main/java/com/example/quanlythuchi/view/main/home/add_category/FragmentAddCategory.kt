package com.example.quanlythuchi.view.main.home.add_category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.databinding.AddCategoryBinding
import com.example.quanlythuchi.view.adapter.AdapterIcon
import com.example.quanlythuchi.view.main.home.category.FragmentCategoryDetail
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class FragmentAddCategory(
) : BaseFragment<AddCategoryBinding, AddCategoryViewModel> (), AddCategoryListener, AdapterIcon.IconOnClickListener {
    override val viewModel: AddCategoryViewModel by viewModels()
    override val layoutID: Int = R.layout.add_category
    private  val adapter by lazy { AdapterIcon(this) }

    private  var typeCategory : String = Fb.CategoryIncome
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            viewModel = this@FragmentAddCategory.viewModel
            rcv.adapter = this@FragmentAddCategory.adapter
            listener = this@FragmentAddCategory
        }
        typeCategory = arguments?.getString(FragmentCategoryDetail.KEY_CATEGORY) ?: Fb.CategoryIncome

        adapter.submitList(viewModel.getListIcon())
    }

    override fun onClick(position: Int, listIcon: MutableList<String>) {
        if (viewModel.idItemRcvIconSelect != -1 && viewBinding.rcv.findViewHolderForAdapterPosition(viewModel.idItemRcvIconSelect) == null) {
            return
        }
        if (viewModel.idItemRcvIconSelect != -1) {
            viewBinding.rcv
                .findViewHolderForAdapterPosition(viewModel.idItemRcvIconSelect)!!
                .itemView.isSelected = false
        }
        viewModel.idItemRcvIconSelect = position
        viewBinding.rcv
            .findViewHolderForAdapterPosition(viewModel.idItemRcvIconSelect)!!
            .itemView.isSelected = true
        viewModel.icon = listIcon[position]

    }

    override fun onClickAddNewCategory() {
        viewModel.addNewCategory(typeCategory = this.typeCategory,
            callback = {
                EventBus.getDefault().post("OKE")
                showToast()
            }
        )
    }
    private fun showToast() {
        Toast.makeText(this.activity, this.activity?.getString(R.string.da_them_danh_muc),Toast.LENGTH_SHORT).show()
    }
    override fun onClickBack() {
        findNavController().popBackStack()
    }
}