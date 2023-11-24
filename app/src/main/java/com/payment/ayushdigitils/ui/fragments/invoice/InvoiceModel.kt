package com.payment.ayushdigitils.ui.fragments.invoice

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class InvoiceModel(
    @field:Json(name = "key")
    val key: String,
    @field:Json(name = "value")
    val value: String?="",
): Parcelable