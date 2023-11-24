package com.payment.ayushdigitils.ui.fragments.report.recharge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.repository.RechargeReportsRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody


class RechargeReportsViewModel(private val repo: RechargeReportsRepository) : BaseViewModel() {

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







