package com.example.quanlythuchi.view.main.report

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quanlythuchi.view.main.report.expense.FragmentReportExpense
import com.example.quanlythuchi.view.main.report.income.FragmentReportInCome

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