package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize




@Parcelize
@JsonClass(generateAdapter = true)
data class PaySprintMoneyTransactionResponse(
    @field:Json(name = "status") val status: String? = "",
    @field:Json(name = "statuscode") val statuscode: String? = "",
    @field:Json(name = "message") val message: String? = "",
    @field:Json(name = "data")
    val data: List<NetworkPaySprintMoneyTransactionDataList>? = listOf(),

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPaySprintMoneyTransactionDataList(

    @field:Json(name = "amount")
    val amount: Int,

    @field:Json(name = "status")
    val status: String?,

    @field:Json(name = "data")
    val data: NetworkPaySprintMoneyTransactionData?,

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPaySprintMoneyTransactionData(

    @field:Json(name = "statuscode")
    val statuscode: String,

    @field:Json(name = "message")
    val message: String,

    @field:Json(name = "rrn")
    val rrn: String?,

    @field:Json(name = "payid")
    val payid: String?,

    ) : Parcelable