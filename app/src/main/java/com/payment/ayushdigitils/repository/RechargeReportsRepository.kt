package com.payment.ayushdigitils.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.payment.ayushdigitils.network.PayApiServices
import com.payment.ayushdigitils.ui.fragments.report.recharge.RechargeReportPagingSource

class RechargeReportsRepository (
    private val apiService: PayApiServices
) {

    fun fetchRechargeReportsResults(
        user_id: String?,
        apptoken: String,
        searchtext: String?,
        status: String?,
        fromdate: String?,
        todate: String?,

    ) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RechargeReportPagingSource(apiService = apiService,user_id =user_id,apptoken= apptoken,searchtext = searchtext,
                    status = status,
                    fromdate = fromdate,
                    todate = todate)
            }
        ).liveData

    companion object {
        const val PAGE_SIZE = 25
    }
}