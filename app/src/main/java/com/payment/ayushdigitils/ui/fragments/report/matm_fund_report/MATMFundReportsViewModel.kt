package com.payment.ayushdigitils.ui.fragments.report.matm_fund_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.payment.ayushdigitils.repository.MATMFundReportsRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody


class MATMFundReportsViewModel(private val repo: MATMFundReportsRepository) : BaseViewModel() {

    val requestReportItem = MutableLiveData<RequestBody>()

    val reportItem = requestReportItem.switchMap { request ->
        if (request == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.fetchRechargeReport(request)
        }
    }
}







