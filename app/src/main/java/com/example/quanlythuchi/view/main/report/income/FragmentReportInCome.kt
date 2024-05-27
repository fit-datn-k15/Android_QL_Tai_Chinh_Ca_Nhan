package com.example.quanlythuchi.view.main.report.income

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentReportIncomeBinding
import com.example.quanlythuchi.view.main.report.ReportViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class FragmentReportInCome : BaseFragment<FragmentReportIncomeBinding,ReportViewModel>(),
    ReportExpenseListener, OnChartValueSelectedListener {
    override val layoutID: Int = R.layout.fragment_report_income
    override val viewModel : ReportViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {

        }
        viewBinding.apply {
            mChart.isRotationEnabled = true
            mChart.description = Description()
            mChart.holeRadius = 35f
            mChart.setTransparentCircleAlpha(0)
            mChart.centerText = "PieChart"
            mChart.setCenterTextSize(10f)
            mChart.setDrawEntryLabels(true)
            addDataSet(mChart)
            mChart.setOnChartValueSelectedListener(this@FragmentReportInCome)
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }


    override fun onNothingSelected() {}

    companion object {
        private fun addDataSet(pieChart: PieChart?) {
            val yEntrys = ArrayList<PieEntry>()
            val xEntrys = ArrayList<String>()
            val yData = floatArrayOf(25f, 40f, 70f)
            val xData = arrayOf("January", "February", "January")
            for (i in yData.indices) {
                yEntrys.add(PieEntry(yData[i], i))
            }
            for (i in xData.indices) {
                xEntrys.add(xData[i])
            }
            val pieDataSet = PieDataSet(yEntrys, "Employee Sales")
            pieDataSet.sliceSpace = 2f
            pieDataSet.valueTextSize = 12f
            val colors = ArrayList<Int>()
            colors.add(Color.GRAY)
            colors.add(Color.BLUE)
            colors.add(Color.RED)
            pieDataSet.colors = colors
            val legend = pieChart!!.legend
            legend.form = Legend.LegendForm.CIRCLE
           // legend.po(Legend.LegendDirection.LEFT_TO_RIGHT)
            val pieData = PieData(pieDataSet)
            pieChart.data = pieData
            pieChart.invalidate()
        }
    }


}