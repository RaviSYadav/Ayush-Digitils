package com.payment.aeps.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AepsResponse(
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "statuscode")
    val statuscode: String?,
    @field:Json(name = "message")
    val message: String?="",
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "txnid")
    val txnid: String?,
    @field:Json(name = "balance")
    val balance: String?,
    @field:Json(name = "rrn")
    val rrn: String?,
    @field:Json(name = "amount")
    val amount: String?,
    @field:Json(name = "aadhar")
    val aadhar: String?,
    @field:Json(name = "bank")
    val bank: String?,
    @field:Json(name = "benename")
    val benename: String?,
    @field:Json(name = "account")
    val account: String?,
    @field:Json(name = "date")
    val date: String?,
    @field:Json(name = "transactionType")
    val transactionType: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "data")
    val data:List<AepsStatementData>? = listOf()
): Parcelable



@kotlinx.parcelize.Parcelize
@JsonClass(generateAdapter = true)
data class AepsStatementData(
    @field:Json(name = "date")
    val date: String?,

    @field:Json(name = "amount")
    val amount: String?,

    @field:Json(name = "txnType")
    val txnType: String?,

    @field:Json(name = "narration")
    val narration: String?
): Parcelable