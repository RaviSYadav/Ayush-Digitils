package com.payment.aeps.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize




@Parcelize
@JsonClass(generateAdapter = true)
data class AepsInvoiceData(
    @field:Json(name = "key")
    val key: String,
    @field:Json(name = "value")
    val value: String?="",
): Parcelable
