package com.qltc.finace.view.main.report

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import com.qltc.finace.R
import com.qltc.finace.base.BaseViewModel
import com.qltc.finace.base.SingleLiveData
import com.qltc.finace.base.TAG
import com.qltc.finace.data.entity.Category
import com.qltc.finace.data.entity.CategoryExpenseDetail
import com.qltc.finace.data.entity.Expense
import com.qltc.finace.data.entity.CategoryOverView
import com.qltc.finace.data.entity.Income
import com.qltc.finace.data.entity.toPieEntry
import com.qltc.finace.data.repository.local.category.CategoryRepository
import com.qltc.finace.data.repository.local.expense.ExpenseRepository
import com.qltc.finace.data.repository.local.income.InComeRepository
import com.qltc.finace.extension.sumMoney
import com.qltc.finace.view.main.calendar.toFinancialRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    @ApplicationContext val applicationContext: Context,
    private val inComeRepository: InComeRepository,
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    var typeReport = SingleLiveData(FragmentReport.CHOOSE_EXPENSE)
    var date: LocalDate = LocalDate.now()
    var listIncome: MutableList<Income> = mutableListOf()
    var listExpense: MutableList<Expense> = mutableListOf()

    var dataPieChar = MutableLiveData<MutableList<PieEntry>>(mutableListOf())
    var listCategory = mutableListOf<Category>()
    var total = MutableLiveData(0L)

    private var listExpenseWithCategoryDec:
            MutableList<CategoryExpenseDetail> = mutableListOf()
    var listIncomeWithCategoryDec:
            MutableLiveData<MutableList<Triple<Category, Long, List<Income>>>> = MutableLiveData(mutableListOf())

    var dataRcv : SingleLiveData<MutableList<CategoryOverView>> = SingleLiveData(mutableListOf())

    fun getAllData(callBack : (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val lExpense = expenseRepository.getAllExpense()
            val lCategory = categoryRepository.getAll()
            val lIncome = inComeRepository.getAllIncome()
            withContext(Dispatchers.Main) {
                listExpense = lExpense
                listCategory = lCategory
                listIncome = lIncome
                callBack.invoke(typeReport.value ?: FragmentReport.CHOOSE_EXPENSE)
                calculateTotal()
            }
        }
    }
//    fun filterExpenseByCategory() {
//        val l = listCategory.map { item ->
//            val l = getExpenseByCategory(item)
//            CategoryExpenseDetail(
//                category = item,
//                totalAmount = l.sumMoney(),
//                listExpense = l
//            )
//        }.toMutableList()
//        listExpenseWithCategoryDec.sortedByDescending { it.totalAmount }.toMutableList()
//    }
    private fun getExpenseByCategory(category: Category): List<Expense> {
        return listExpense.filter { it.idCategory == category.idCategory }
    }
    private fun getExpenseByMonth(month: YearMonth): List<Expense> {
        return listExpense.filter { it.getYearMonth() == month.toString() }
    }

    fun prepareDataPieChartExpenseByMonth(month: YearMonth) {
        viewModelScope.launch(Dispatchers.IO) {
            val listCategoryExpenseDetailOfMonthDec: MutableList<CategoryExpenseDetail> =
                getExpenseWithCategoryOfMonth(month)
            listCategoryExpenseDetailOfMonthDec.sortByDescending { it.totalAmount }

            val listPieEntry = addItemEntry(listCategoryExpenseDetailOfMonthDec)
            addItemEntryOther(listCategoryExpenseDetailOfMonthDec)?.let {
                listPieEntry.add(POSITION_ITEM_OTHER,it)
            }

            withContext(Dispatchers.Main) {
                this@ReportViewModel.listCategoryExpenseDetailDec = listCategoryExpenseDetailOfMonthDec
                this@ReportViewModel.dataPieChar.value = listPieEntry
            }
        }
    }
    private fun addItemEntry(listCategoryExpenseDetail: MutableList<CategoryExpenseDetail>) : MutableList<PieEntry> {
        val listPieEntry = listCategoryExpenseDetail
            .take(COUNT_ITEM_PIE_CHART + 1)
            .map { it.toPieEntry() }
            .toMutableList()
        return listPieEntry
    }
    private fun getExpenseWithCategoryOfMonth(month: YearMonth): MutableList<CategoryExpenseDetail> {
        val listExpenseOfMonth = getExpenseByMonth(month)
        return listExpenseOfMonth.groupBy { it.idCategory }.map { (idCategory, listExpense) ->
                CategoryExpenseDetail(
                    category = getCategoryObject(idCategory),
                    totalAmount = listExpense.sumMoney(),
                    listExpense = listExpense
                )
            }.toMutableList()
    }

    private fun getCategoryObject(idCategory: String?) = listCategory.first { it.idCategory == idCategory }
    private fun addItemEntryOther(list: MutableList<CategoryExpenseDetail>) : PieEntry? {
        if (list.size > MAX_ITEM_IN_PIE_CHART) {
            var total = 0L
            val lExpenseOther = mutableListOf<Expense>()
            for (i in MAX_ITEM_IN_PIE_CHART until list.size) {
                total += list[i].totalAmount
                lExpenseOther.addAll(list[i].listExpense?: mutableListOf())
            }
            return PieEntry(
                    total.toFloat(),
                    applicationContext.getString(R.string.other),
                    lExpenseOther
            )
        }
        return null
    }
    fun prepareRecyclerViewExpense(yearMonth: YearMonth) {
        val l = mutableListOf<CategoryOverView>()
        for (item in listCategoryExpenseDetailDec) {
            val lExpense = item.listExpense?.filter { expense ->
                yearMonth.toString() == expense.getYearMonth()
            }
            if (lExpense?.size != 0) {
                val lFinancialRecord =
                    lExpense?.map { expense -> expense.toFinancialRecord(item.category) }
                l.add(
                    CategoryOverView(
                        total = item.totalAmount,
                        category = item.category!!,
                        listRecord = lFinancialRecord
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
        const val COUNT_ITEM_PIE_CHART = 4
    }
    fun test() {
        TODO()
    }
}