package com.example.quanlythuchi.view.fragment.report

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quanlythuchi.view.fragment.report.expense.FragmentReportExpense
import com.example.quanlythuchi.view.fragment.report.income.FragmentReportInCome

class AdapterReport(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position) {
            0-> FragmentReportExpense()
            else -> FragmentReportInCome()
        }
    }
}