package com.example.quanlythuchi.view.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FagmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : BaseFragment<FagmentHomeBinding, HomeViewModel>(),HomeListener {
    override val layoutID: Int = R.layout.fagment_home
    override val viewModel: HomeViewModel by viewModels()
    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }
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



    }
}
