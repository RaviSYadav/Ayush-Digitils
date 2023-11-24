package com.payment.ayushdigitils.ui.fragments.report.dmt_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.repository.DMTReportsRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody


class DMTReportsViewModel(private val repo: DMTReportsRepository) : BaseViewModel() {

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







