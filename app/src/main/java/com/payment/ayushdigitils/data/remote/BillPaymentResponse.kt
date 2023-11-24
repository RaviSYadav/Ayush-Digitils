package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class BillPaymentResponse(
    @field:Json(name = "message") val message: String,
    @field:Json(name = "txnid") val txnid: String,
    @field:Json(name = "rrn") val rrn: String,

) : Parcelable