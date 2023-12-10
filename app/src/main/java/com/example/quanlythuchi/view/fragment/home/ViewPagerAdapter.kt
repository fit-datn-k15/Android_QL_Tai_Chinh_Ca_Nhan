package com.example.quanlythuchi.view.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quanlythuchi.view.fragment.home.expense.FragmentExpense
import com.example.quanlythuchi.view.fragment.home.income.IncomeFragment

class ViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FragmentExpense()
            1 -> return  IncomeFragment()
            else -> return FragmentExpense()
        }
    }
}