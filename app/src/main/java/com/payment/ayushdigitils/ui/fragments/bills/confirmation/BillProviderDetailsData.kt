package com.payment.ayushdigitils.ui.fragments.bills.confirmation

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class BillProviderDetailsData(
    @field:Json(name = "provider_id") val provider_id: String,
    @field:Json(name = "provider_name") val provider_name: String,
    @field:Json(name = "provider_type") val provider_type: String,
    @field:Json(name = "type") val type: String = "payment",
    @field:Json(name = "consumer_name") val consumer_name: String,
    @field:Json(name = "due_date") val due_date: String,
    @field:Json(name = "bill_amount") val bill_amount: String,
    @field:Json(name = "t_pin") val t_pin: String,
    @field:Json(name = "bu") val bu: String = "",
    @field:Json(name = "bbps_mode") val bbps_mode: String = "",
    @field:Json(name = "TransactionId") val TransactionId: String = "",
): Parcelable
