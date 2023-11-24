package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RechargeReportResponse(
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "message") val message: String?,
    @field:Json(name = "pages") val pages: Int,
    @field:Json(name = "data") val data:List<RechargeReportData> = listOf(),
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class RechargeReportData(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "number") val number: String,
    @field:Json(name = "mobile") val mobile: String,
    @field:Json(name = "amount") val amount: String,
    @field:Json(name = "charge") val charge: String,
    @field:Json(name = "profit") val profit: String,
    @field:Json(name = "txnid") val txnid: String,
    @field:Json(name = "payid") val payid: String?,
    @field:Json(name = "refno") val refno: String?,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "via") val via: String,
    @field:Json(name = "balance") val balance: String,
    @field:Json(name = "trans_type") val trans_type: String,
    @field:Json(name = "rtype") val rtype: String?,
    @field:Json(name = "product") val product: String,
    @field:Json(name = "providername") val providername: String,
    @field:Json(name = "option1") val option1: String?,
    @field:Json(name = "option2") val option2: String?,
    @field:Json(name = "option3") val option3: String?,
    @field:Json(name = "option4") val option4: String?,
    @field:Json(name = "option5") val option5: String?,
    @field:Json(name = "gst") val gst: String?,
    @field:Json(name = "tds") val tds: String?,
    @field:Json(name = "provider_id") val provider_id: String?,
    @field:Json(name = "remark") val remark: String?,
    @field:Json(name = "username") val username: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "created_at") val created_at: String,
    @field:Json(name = "updated_at") val updated_at: String,


    ) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class ReportFundBankData(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: Int,
    @field:Json(name = "account") val account: Int,
    @field:Json(name = "ifsc") val ifsc: Int,
    @field:Json(name = "branch") val branch: Int,
) : Parcelable