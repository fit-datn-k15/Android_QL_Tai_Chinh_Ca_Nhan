package com.example.quanlythuchi.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.TAG
import com.example.quanlythuchi.databinding.FagmentHomeBinding
import com.example.quanlythuchi.view.main.home.income.IncomeFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : BaseFragment<FagmentHomeBinding, BaseHomeViewModel>(),HomeListener {
    override val layoutID: Int = R.layout.fagment_home
    override val viewModel: ShareHomeViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {
            listener = this@FragmentHome
        }
        viewBinding.vpgHome.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(viewBinding.tabLayout,viewBinding.vpgHome) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.expense)
                1 -> tab.text = getString(R.string.income)
            }
        }.attach()
        onViewPagerChange()
    }
    private fun onViewPagerChange() {
        viewBinding.vpgHome.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == FRAGMENT_EXPENSE) {
                    viewModel.typeCurrentFragment = FRAGMENT_EXPENSE
                }
                else {
                    viewModel.typeCurrentFragment = FRAGMENT_INCOME
                }
            }
        })
    }
    companion object {
        const val FRAGMENT_EXPENSE = 0
        const val FRAGMENT_INCOME = 1
    }
}

