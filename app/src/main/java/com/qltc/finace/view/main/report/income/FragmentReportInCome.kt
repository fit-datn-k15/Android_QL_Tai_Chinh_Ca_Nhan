//package com.qltc.finace.view.main.report.income
//
//import android.graphics.Color
//import android.graphics.Typeface
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.activityViewModels
//import com.qltc.finace.R
//import com.qltc.finace.base.BaseFragment
//import com.qltc.finace.databinding.FragmentReportIncomeBinding
//import com.qltc.finace.view.adapter.AdapterExpenseIncomeReport
//import com.qltc.finace.view.main.calendar.FinancialRecord
//import com.qltc.finace.view.main.report.chart.PercentFormatter
//import com.qltc.finace.view.main.report.chart.PieChartCustomRendederer
//import com.qltc.finace.view.main.report.ReportViewModel
//import com.github.mikephil.charting.components.Description
//import com.github.mikephil.charting.components.Legend
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.PieData
//import com.github.mikephil.charting.data.PieDataSet
//import com.github.mikephil.charting.highlight.Highlight
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener
//import dagger.hilt.android.AndroidEntryPoint
//import java.time.YearMonth
//
//
//@AndroidEntryPoint
//class FragmentReportInCome : BaseFragment<FragmentReportIncomeBinding, ReportViewModel>(),
//    ReportExpenseListener, OnChartValueSelectedListener,AdapterExpenseIncomeReport.OnClickListener {
//    override val layoutID: Int = R.layout.fragment_report_income
//    override val viewModel: ReportViewModel by activityViewModels()
//    private val valueFormatter by lazy { PercentFormatter() }
//    private val adapterRcv by lazy { AdapterExpenseIncomeReport(this) }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewBinding.lifecycleOwner = this
//        viewBinding.apply {
//
//        }
//       addDataSet()
//        viewModel.listDataIncomePieChar.observe(viewLifecycleOwner) {
//            val pieDataSet = PieDataSet(it, "Khoản thu")
//            pieDataSet.apply {
//                sliceSpace = 2f
//                valueTextSize = 12f
//                colors = listOf(
//                    context?.getColor(R.color.orange) ?: Color.GRAY,
//                    context?.getColor(R.color.green_700) ?: Color.GREEN,
//                    Color.RED,
//                    context?.getColor(R.color.color_FF8126) ?: Color.MAGENTA,
//                    Color.YELLOW,
//                    Color.CYAN,
//                    Color.BLACK
//                )
//            }
//            // set đuườn line mô tả biểu đồ
//            pieDataSet.apply {
//                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
//                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
//                valueLinePart1Length = 0.8f
//                valueLinePart2Length = 0.8f
//                valueLinePart1OffsetPercentage = 70f
//                valueLineColor = Color.BLACK
//                valueTextSize = 9f
//
//            }
//            pieDataSet.sliceSpace = 0f
//            viewBinding.mChart.minAngleForSlices = 5f
//            val pieData = PieData(pieDataSet)
//
//            pieData.setValueFormatter(valueFormatter)
//            viewBinding.mChart.apply {
//                data = pieData
//                notifyDataSetChanged()
//                refreshDrawableState()
//                setEntryLabelColor(context.getColor(R.color.black60))
//                setEntryLabelTextSize(10f)
//                setEntryLabelTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
//                invalidate()
//            }
//        }
//       viewModel.filterDataIncomeByMonth(YearMonth.from(viewModel.date))
//    }
//
//    override fun onValueSelected(e: Entry?, h: Highlight?) {
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        viewModel.rcvIncomePrepare(YearMonth.from(viewModel.date))
//    }
//
//    override fun onNothingSelected() {}
//
//    private fun addDataSet() {
//        viewBinding.mChart.apply {
//            isRotationEnabled = true  // cho phep xoay biểu đồ
//            // thiet lap mo tả cho biểu do
//            holeRadius = 40f              // bán kính của lỗ trống ở giữa bieiể đồ
//            setTransparentCircleAlpha(50)  // đặt độ trong suouốt cho vòng tròn bên ngoài
//            centerText = "Khoản thu"   // vaăn bản ở giữa biểu đồ
//            setCenterTextSize(10f)
//            setEntryLabelColor(R.color.black80)
//            setDrawEntryLabels(true) // hiển thị nhãn của các muục dữ liệu
//            setUsePercentValues(true)
//            isRotationEnabled = false
//            description = getDescriptionPieChart()
//            setExtraOffsets(15f,15f,15f,15f)
//            setOnChartValueSelectedListener(this@FragmentReportInCome)
//            renderer = PieChartCustomRendederer(viewBinding.mChart, viewBinding.mChart.animator, viewBinding.mChart.viewPortHandler)
//
//        }
//        viewBinding.mChart.apply {
//            legend.form = Legend.LegendForm.CIRCLE
//            legend.orientation = Legend.LegendOrientation.VERTICAL
//            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
//            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//            legend.setDrawInside(false)
//            legend.xEntrySpace = 7f
//            legend.yEntrySpace = 0f
//            legend.yOffset = 10f
//            legend.isEnabled = false
//            legend.textColor = Color.BLACK
//            //    invalidate()
//        }
//    }
//
//    override fun onClickItemEI(item: FinancialRecord) {
//
//    }
//    private fun getDescriptionPieChart() : Description {
//        val d = Description()
//        d.text = ""
//        return d
//    }
//}