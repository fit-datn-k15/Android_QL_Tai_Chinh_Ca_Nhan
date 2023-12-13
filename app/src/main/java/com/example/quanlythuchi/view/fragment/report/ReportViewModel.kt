package com.example.quanlythuchi.view.fragment.report

import com.example.quanlythuchi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel
class ReportViewModel @Inject constructor(

) : BaseViewModel() {
    var date : LocalDate = LocalDate.now()
}