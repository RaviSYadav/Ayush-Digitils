package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class AEPSFundReportResponse(
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "message") val message: String?,
    @field:Json(name = "pages") val pages: Int,
    @field:Json(name = "data") val data:List<AEPSFundReportData> = listOf(),
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class AEPSFundReportData(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "bank") val bank: String?,
    @field:Json(name = "account") val account: String?,
    @field:Json(name = "ifsc") val ifsc: String?,
    @field:Json(name = "apitxnid") val apitxnid: String?,
    @field:Json(name = "pay_type") val pay_type: String?,
    @field:Json(name = "payoutid") val payoutid: String?,
    @field:Json(name = "payoutref") val payoutref: String?,
    @field:Json(name = "amount") val amount: String?,
    @field:Json(name = "remark") val remark: String?,
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "mode") val mode: String?,
    @field:Json(name = "updated_at") val updated_at: String?,
    @field:Json(name = "username") val username: String?,


    ) : Parcelable