package com.example.quanlythuchi.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.data.room.AppDatabase
import com.example.quanlythuchi.databinding.FagmentHomeBinding
import com.example.quanlythuchi.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentHome : BaseFragment<FagmentHomeBinding, HomeViewModel>(),HomeListener {
    override val layoutID: Int = R.layout.fagment_home
    override val viewModel: HomeViewModel by viewModels()

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
