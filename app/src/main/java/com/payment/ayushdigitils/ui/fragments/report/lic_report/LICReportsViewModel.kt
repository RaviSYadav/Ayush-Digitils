package com.payment.ayushdigitils.ui.fragments.report.lic_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.repository.LICReportsRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody


class LICReportsViewModel(private val repo: LICReportsRepository) : BaseViewModel() {

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







