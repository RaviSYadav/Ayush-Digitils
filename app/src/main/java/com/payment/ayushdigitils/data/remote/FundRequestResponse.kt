package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class FundRequestResponse(
    @field:Json(name = "message") val message: String? = "Something went wrong, try again",
    @field:Json(name = "txnid") val txnid: String,
) : Parcelable