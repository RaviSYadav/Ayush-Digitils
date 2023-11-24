package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class AEPSReportResponse(
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "message") val message: String?,
    @field:Json(name = "pages") val pages: Int,
    @field:Json(name = "data") val data:List<AEPSReportData> = listOf() ,
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class AEPSReportData(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "mobile") val mobile: String,
    @field:Json(name = "aadhar") val aadhar: String?,
    @field:Json(name = "txnid") val txnid: String,
    @field:Json(name = "amount") val amount: String,
    @field:Json(name = "charge") val charge: String,
    @field:Json(name = "bank") val bank: String?,
    @field:Json(name = "payid") val payid: String,
    @field:Json(name = "refno") val refno: String,
    @field:Json(name = "terminalid") val terminalid: String?,
    @field:Json(name = "balance") val balance: String?,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "trans_type") val trans_type: String?,
    @field:Json(name = "aepstype") val aepstype: String,
    @field:Json(name = "via") val via: String,
    @field:Json(name = "product") val product: String,
    @field:Json(name = "created_at") val created_at: String,
    @field:Json(name = "updated_at") val updated_at: String,

) : Parcelable
