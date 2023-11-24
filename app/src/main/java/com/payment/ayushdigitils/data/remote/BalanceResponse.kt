package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class BalanceResponse (

    @field:Json(name = "status") val status: String,
    @field:Json(name = "message") val message: String,

    @field:Json(name = "data")
    val data: NetworkBalanceData?= null
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkBalanceData(

    @field:Json(name = "pasprintonboard")
    val pasprintonboard: String,

    @field:Json(name = "mainwallet")
    val mainwallet: Double,

    @field:Json(name = "aepsbalance")
    val aepsbalance: Double,

    @field:Json(name = "upimerchent")
    val upimerchent: String,

    @field:Json(name = "pId")
    val pId: String,

    @field:Json(name = "apiKey")
    val apiKey: String,

    @field:Json(name = "lat")
    val lat: Double,

    @field:Json(name = "lon")
    val lon: Double,

    @field:Json(name = "mCode")
    val mCode: String,

    @field:Json(name = "isdoneaepsauth")
    val isdoneaepsauth: String,

    ) : Parcelable
