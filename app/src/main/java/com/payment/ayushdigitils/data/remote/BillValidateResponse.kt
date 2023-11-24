package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class BillValidateResponse(
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "message") val message: String?,

    @field:Json(name = "data") val data: BillValidateData?= null,
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class BillValidateData(
    @field:Json(name = "customername") val customername: String,
    @field:Json(name = "dueamount") val dueamount: String,
    @field:Json(name = "duedate") val duedate: String,
    @field:Json(name = "TransactionId") val TransactionId: String? = "",
    @field:Json(name = "mode") val mode: String?,
) : Parcelable


