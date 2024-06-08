package com.example.quanlythuchi.view.main.report

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.base.TAG
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.entity.TotalCategory
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.extension.toLocalDate
import com.example.quanlythuchi.extension.toMonthYearString
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ReportViewModel @Inject constructor(
    @ApplicationContext val applicationContext: Context,
    private val inComeRepository: InComeRepository,
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    var isFragment = FragmentReport.FRAGMENT_EXPENSE
    var date: LocalDate = LocalDate.now()
    var listIncome: MutableList<Income> = mutableListOf()
    var listExpense: MutableList<Expense> = mutableListOf()

    var listDataExpensePieChar = MutableLiveData<MutableList<PieEntry>>(mutableListOf())
    var listDataIncomePieChar = MutableLiveData<MutableList<PieEntry>>(mutableListOf())

    var listCategory = mutableListOf<Category>()
    var mapCategory = mapOf<String?,Category>()
    var total = MutableLiveData(0L)

    var listExpenseWithCategoryDec:
            MutableLiveData<MutableList<Triple<Category, Long, List<Expense>>>> = MutableLiveData(mutableListOf())
    var listIncomeWithCategoryDec:
            MutableLiveData<MutableList<Triple<Category, Long, List<Income>>>> = MutableLiveData(mutableListOf())

    var dataRcv : SingleLiveData<MutableList<TotalCategory>> = SingleLiveData(mutableListOf())

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            val lExpense = expenseRepository.getAllExpense()
            val lCategory = categoryRepository.getAll()
            val lIncome = inComeRepository.getAllIncome()
            withContext(Dispatchers.Main) {
                listExpense = lExpense
                listCategory = lCategory
                listIncome = lIncome
                filterDataExpenseByMonth(YearMonth.from(date))

                calculateTotal()
            }
        }
    }

    private fun filterDataExpense() {
        viewModelScope.launch(Dispatchers.IO) {
            val mapExpenseByIdCategory: Map<String?, List<Expense>> =
                listExpense.groupBy { it.idCategory }
            val mapCategory = listCategory.associateBy { it.idCategory }
            // list chứa danh mục, tổng số tiền của danh mục đó và list các khoản chi thuộc nó
            var listExpenseWithCategory: MutableList<Triple<Category, Long, List<Expense>>> =
                mapExpenseByIdCategory.mapNotNull { item ->
                    val key: Category? = mapCategory[item.key]
                    val value: List<Expense>? = mapExpenseByIdCategory[item.key]
                    if (key != null && value != null)
                        Triple(key, value.sumOf { itemExpense -> itemExpense.expense ?: 0 },value)
                    else null
                }.sortedByDescending { triple ->
                    triple.second
                }.toMutableList()
            // chuẩn bị dữ liệu cho biểu đồ
            val listPieEntry = mutableListOf<PieEntry>()
            for (i in 0..4) {
                if (listExpenseWithCategory.size <= i )
                    break
                val item = listExpenseWithCategory[i]
                listPieEntry.add(PieEntry(
                    item.second.toFloat(),
                    item.first.title,
                    item.third
                ))
            }
            if ( listExpenseWithCategory.size > MAX_ITEM_IN_PIE_CHART) {
                var dataOther = 0L
                val lExpenseOther = mutableListOf<Expense>()
                for (i in MAX_ITEM_IN_PIE_CHART until listExpenseWithCategory.size) {
                    dataOther += listExpenseWithCategory[i].second ?: 0
                    lExpenseOther.addAll(listExpenseWithCategory[i].third)
                }
                listPieEntry.add(
                    POSITION_ITEM_OTHER,
                    PieEntry(
                        dataOther.toFloat(),
                        applicationContext.getString(R.string.other),
                        lExpenseOther
                ))
            }

            withContext(Dispatchers.Main) {
                this@ReportViewModel.apply {
                    listExpenseWithCategoryDec.postValue(listExpenseWithCategory)
                    listDataExpensePieChar.postValue(listPieEntry)
                    this.mapCategory = mapCategory
                }
            }
        }
    }
    fun filterDataIncomeByMonth(month : YearMonth) {
        viewModelScope.launch(Dispatchers.IO) {

            val mapIncomeByIdCategoryOfMonth: Map<String?, List<Income>> =
                listIncome.filter { income ->
                    income.date.toLocalDate().toMonthYearString() == month.toString()
                }.groupBy {
                    it.idCategory
                }
            val mapCategory: Map<String?, Category> = listCategory.associateBy { it.idCategory }

            /*
                list chứa danh mục, tổng số tiền của danh mục đó và list các khoản chi thuộc nó
             */
            var listIncomeWithCategory: MutableList<Triple<Category, Long, List<Income>>> =
                mapIncomeByIdCategoryOfMonth.mapNotNull { item ->
                    val key: Category? = mapCategory[item.key]
                    val value: List<Income>? = mapIncomeByIdCategoryOfMonth[item.key]
                    if (key != null && value != null)
                        Triple(key, value.sumOf { itemIncome -> itemIncome.income ?: 0 }, value)
                    else null
                }.sortedByDescending { triple ->
                    triple.second
                }.toMutableList()
            /*
                chuẩn bị dữ liệu cho biểu đồ
             */
            val listPieEntry = mutableListOf<PieEntry>()
            for (i in 0..4) {
                if (listIncomeWithCategory.size <= i)
                    break
                val item = listIncomeWithCategory[i]
                listPieEntry.add(
                    PieEntry(
                        item.second.toFloat(),
                        item.first.title,
                        item
                    )
                )
            }
            if (listIncomeWithCategory.size > MAX_ITEM_IN_PIE_CHART) {
                var dataOther = 0L
                val lIncomeOther = mutableListOf<Income>()
                for (i in MAX_ITEM_IN_PIE_CHART until listIncomeWithCategory.size) {
                    dataOther += listIncomeWithCategory[i].second ?: 0
                    lIncomeOther.addAll(listIncomeWithCategory[i].third)
                }
                listPieEntry.add(
                    POSITION_ITEM_OTHER,
                    PieEntry(
                        dataOther.toFloat(),
                        applicationContext.getString(R.string.other),
                        lIncomeOther
                    )
                )
            }

            withContext(Dispatchers.Main) {
                this@ReportViewModel.listIncomeWithCategoryDec.value = listIncomeWithCategory
                this@ReportViewModel.listDataIncomePieChar.value = listPieEntry
                rcvIncomePrepare(month)
            }
        }
    }
    fun filterDataExpenseByMonth(month: YearMonth) {
        viewModelScope.launch(Dispatchers.IO) {

            val mapExpenseByIdCategoryOfMonth: Map<String?, List<Expense>> =
                listExpense.filter { expense ->
                    expense.date.toLocalDate().toMonthYearString() == month.toString()
                }.groupBy {
                    it.idCategory
                }
            val mapCategory: Map<String?, Category> = listCategory.associateBy { it.idCategory }

            /*
                list chứa danh mục, tổng số tiền của danh mục đó và list các khoản chi thuộc nó
             */
            var listExpenseWithCategory: MutableList<Triple<Category, Long, List<Expense>>> =
                mapExpenseByIdCategoryOfMonth.mapNotNull { item ->
                    val key: Category? = mapCategory[item.key]
                    val value: List<Expense>? = mapExpenseByIdCategoryOfMonth[item.key]
                    if (key != null && value != null)
                        Triple(key, value.sumOf { itemExpense -> itemExpense.expense ?: 0 }, value)
                    else null
                }.sortedByDescending { triple ->
                    triple.second
                }.toMutableList()
            /*
                chuẩn bị dữ liệu cho biểu đồ
             */
            val listPieEntry = mutableListOf<PieEntry>()
            for (i in 0..4) {
                if (listExpenseWithCategory.size <= i)
                    break
                val item = listExpenseWithCategory[i]
                listPieEntry.add(
                    PieEntry(
                        item.second.toFloat(),
                        item.first.title,
                        item
                    )
                )
            }
            if (listExpenseWithCategory.size > MAX_ITEM_IN_PIE_CHART) {
                var dataOther = 0L
                val lExpenseOther = mutableListOf<Expense>()
                for (i in MAX_ITEM_IN_PIE_CHART until listExpenseWithCategory.size) {
                    dataOther += listExpenseWithCategory[i].second ?: 0
                    lExpenseOther.addAll(listExpenseWithCategory[i].third)
                }
                listPieEntry.add(
                    POSITION_ITEM_OTHER,
                    PieEntry(
                        dataOther.toFloat(),
                        applicationContext.getString(R.string.other),
                        lExpenseOther
                    )
                )
            }

            withContext(Dispatchers.Main) {
                this@ReportViewModel.listExpenseWithCategoryDec.value = listExpenseWithCategory
                this@ReportViewModel.listDataExpensePieChar.value = listPieEntry
                rcvExpensePrepare(month)
            }
        }
    }

    fun rcvExpensePrepare(yearMonth: YearMonth) {
        val l = mutableListOf<TotalCategory>()
        val m = yearMonth.toString()
        for (item in listExpenseWithCategoryDec.value!!) {
            val data = item.third
            val lExpense = mutableListOf<Expense>()
            data.forEach { expense ->
                if (m == expense.date.toLocalDate().toMonthYearString()) {
                    lExpense.add(expense)
                }
            }
            if (lExpense.size != 0) {
                l.add(
                    TotalCategory(
                        total = item.second,
                        category = item.first,
                        data = item.third
                    )
                )
            }
        }
        dataRcv.postValue(l)
    }

    fun rcvIncomePrepare(yearMonth: YearMonth) {
        val l = mutableListOf<TotalCategory>()
        val m = yearMonth.toString()
        for (item in listIncomeWithCategoryDec.value!!) {
            val data = item.third
            val lIncome = mutableListOf<Income>()
            data.forEach { income ->
                if (m == income.date.toLocalDate().toMonthYearString()) {
                    lIncome.add(income)
                }
            }
            if (lIncome.size != 0) {
                l.add(
                    TotalCategory(
                        total = item.second,
                        category = item.first,
                        data = item.third
                    )
                )
            }
        }
        dataRcv.postValue(l)
    }
    private fun calculateTotal() {
        viewModelScope.launch(Dispatchers.IO) {
            var total = 0L
            for (item in listIncome) {
                item.income?.let { total += it }
            }
            for (item in listExpense) {
                item.expense?.let { total -= it }
            }
            withContext(Dispatchers.Main) {
                Log.d(TAG, "calculateTotal: $total")
                this@ReportViewModel.total.value = total
            }
        }
    }
    companion object {
        const val MAX_ITEM_IN_PIE_CHART = 6
        const val POSITION_ITEM_OTHER = 2
    }
}