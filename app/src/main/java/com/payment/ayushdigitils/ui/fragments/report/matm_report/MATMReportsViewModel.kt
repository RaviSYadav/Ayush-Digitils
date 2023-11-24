package com.payment.ayushdigitils.ui.fragments.report.matm_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.repository.MATMReportsRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody


class MATMReportsViewModel(private val repo: MATMReportsRepository) : BaseViewModel() {
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







