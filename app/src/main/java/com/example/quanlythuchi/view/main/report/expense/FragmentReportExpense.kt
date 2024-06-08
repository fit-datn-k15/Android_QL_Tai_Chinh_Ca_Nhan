package com.example.quanlythuchi.view.main.report.expense

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.fragment.app.activityViewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentReportExpenseBinding
import com.example.quanlythuchi.view.main.report.PercentFormatter
import com.example.quanlythuchi.view.main.report.PieChartCustomRendederer
import com.example.quanlythuchi.view.main.report.ReportViewModel
import com.example.quanlythuchi.view.main.report.income.ReportExpenseListener
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.play.integrity.internal.h
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentReportExpense : BaseFragment<FragmentReportExpenseBinding, ReportViewModel>(),
    ReportExpenseListener, OnChartValueSelectedListener {
    override val layoutID: Int = R.layout.fragment_report_expense
    override val viewModel : ReportViewModel by activityViewModels()
    private val valueFormatter by lazy { PercentFormatter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this
        viewBinding.apply {

        }
        addDataSet()
        viewModel.listDataExpensePieChar.observe(viewLifecycleOwner) {
            val pieDataSet = PieDataSet(it, "Khoản thu")
            pieDataSet.apply {
                sliceSpace = 2f
                valueTextSize = 12f
                colors = listOf(Color.GRAY, Color.BLUE, Color.RED, Color.MAGENTA,Color.YELLOW,Color.CYAN, Color.BLACK)
            }
            // set đuườn line mô tả biểu đồ
            pieDataSet.apply {
                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                valueLinePart1Length = 0.8f
                valueLinePart2Length = 0.8f
                valueLinePart1OffsetPercentage = 70f
                valueLineColor = Color.BLACK
                valueTextSize = 9f

            }
            pieDataSet.sliceSpace = 0f
            viewBinding.mChart.minAngleForSlices = 5f
            val pieData = PieData(pieDataSet)

            pieData.setValueFormatter(valueFormatter)
            viewBinding.mChart.apply {
                data = pieData
                notifyDataSetChanged()
                refreshDrawableState()
                setEntryLabelColor(context.getColor(R.color.black60))
                setEntryLabelTextSize(10f)
                setEntryLabelTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
                invalidate()
            }
        }

    }

    override fun onValueSelected(p0: Entry?, p1: Highlight?) {
        if (p0 != null && p0 is PieEntry) {
            // Lấy tọa độ x và y của điểm được click
            val x = p1?.x ?: 0f
            val y = p1?.y ?: 0f
            // Hiển thị popup tại điểm được click
         //   displayPopupWindow(x, y)
        }
    }

    override fun onNothingSelected() {

    }
    private fun addDataSet() {
        viewBinding.mChart.apply {
            isRotationEnabled = true  // cho phep xoay biểu đồ
            // thiet lap mo tả cho biểu do
            holeRadius = 40f              // bán kính của lỗ trống ở giữa bieiể đồ
            setTransparentCircleAlpha(50)  // đặt độ trong suouốt cho vòng tròn bên ngoài
            centerText = "Khoản chi"   // vaăn bản ở giữa biểu đồ
            setCenterTextSize(10f)
            setEntryLabelColor(R.color.black80)
            setDrawEntryLabels(true) // hiển thị nhãn của các muục dữ liệu
            setUsePercentValues(true)

            description = getDescriptionPieChart()
            setExtraOffsets(20f,20f,20f,20f)
            setOnChartValueSelectedListener(this@FragmentReportExpense)
            renderer = PieChartCustomRendederer(viewBinding.mChart, viewBinding.mChart.animator, viewBinding.mChart.viewPortHandler)

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
            legend.textColor = Color.BLACK
            //    invalidate()
        }
    }
    private fun getDescriptionPieChart() : Description {
        val d = Description()
        d.text = ""
        return d
    }
    private fun displayPopupWindow(x: Float, y: Float) {
        val popup = PopupWindow(this.activity)
        val layout: View = layoutInflater.inflate(R.layout.popup, null)
        popup.contentView = layout
        popup.height = WindowManager.LayoutParams.WRAP_CONTENT
        popup.width = WindowManager.LayoutParams.WRAP_CONTENT
        popup.isOutsideTouchable = true
        popup.isFocusable = true
        val chartLocation = IntArray(2)
        viewBinding.mChart.getLocationOnScreen(chartLocation)
        popup.showAtLocation(requireActivity().window.decorView, Gravity.NO_GRAVITY, x.toInt(), y.toInt())
    }
 }