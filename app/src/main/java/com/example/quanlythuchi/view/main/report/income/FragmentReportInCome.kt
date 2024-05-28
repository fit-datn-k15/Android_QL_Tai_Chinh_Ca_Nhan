package com.example.quanlythuchi.view.main.report.income

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentReportIncomeBinding
import com.example.quanlythuchi.view.main.report.ReportViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentReportInCome : BaseFragment<FragmentReportIncomeBinding, ReportViewModel>(),
    ReportExpenseListener, OnChartValueSelectedListener {
    override val layoutID: Int = R.layout.fragment_report_income
    override val viewModel: ReportViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {

        }
        viewBinding.apply {
            mChart.isRotationEnabled = false  // cho phep xoay biểu đồ
         // thiet lap mo tả cho biểu do
            mChart.holeRadius = 30f              // bán kính của lỗ trống ở giữa bieiể đồ
            mChart.setTransparentCircleAlpha(10)  // đặt độ trong suouốt cho vòng tròn bên ngoài
            mChart.centerText = "PieChart"   // vaăn bản ở giữa biểu đồ
            mChart.setCenterTextSize(10f)
            mChart.setEntryLabelColor(R.color.black80)
            mChart.setDrawEntryLabels(true) // hiển thị nhãn của các muục dữ liệu
            addDataSet()
            mChart.setOnChartValueSelectedListener(this@FragmentReportInCome)
        }
        viewModel.listDataExpensePieChar.observe(viewLifecycleOwner) {
            val pieDataSet = PieDataSet(it, "Employee Sales")
            pieDataSet.apply {
                sliceSpace = 2f
                valueTextSize = 12f
                colors = listOf(Color.GRAY, Color.BLUE, Color.RED, Color.MAGENTA,Color.YELLOW,Color.CYAN, Color.BLACK)
            }
            pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

            // Use lines to connect labels with slices

            pieDataSet.valueLineColor = Color.BLACK
            viewBinding.mChart.data = PieData(pieDataSet)
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }


    override fun onNothingSelected() {}

    private fun addDataSet() {
        val data = arrayListOf(
            PieEntry(25f, "January"),
            PieEntry(25f, "February"),
            PieEntry(50f, "January")
        )

        val pieDataSet = PieDataSet(data, "Employee Sales")

        pieDataSet.apply {
            sliceSpace = 2f
            valueTextSize = 12f
            colors = listOf(Color.GRAY, Color.BLUE, Color.RED)
        }
        viewBinding.mChart.apply {
            legend.form = Legend.LegendForm.CIRCLE
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.setDrawInside(false)
            legend.xEntrySpace = 7f
            legend.yEntrySpace = 0f
            legend.yOffset = 10f
            legend.isEnabled = false
            this@apply.data = PieData(pieDataSet)
        //    invalidate()
        }
    }

}