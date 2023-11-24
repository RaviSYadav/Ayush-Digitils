package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MAtmInitiateResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "message") val message: String?,
    @field:Json(name = "data")
    val data: NetworkMAtmInitiateData?= null
    ): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkMAtmInitiateData(

    @field:Json(name = "partnerId")
    val partnerId: String,

    @field:Json(name = "apiKey")
    val apiKey: String,

    @field:Json(name = "merchantCode")
    val merchantCode: String,

    @field:Json(name = "UserId")
    val UserId: String,

    @field:Json(name = "bcEmailId")
    val bcEmailId: String,

    @field:Json(name = "referenceNumber")
    val referenceNumber: String,

    @field:Json(name = "clientrefid")
    val clientrefid: String,

    @field:Json(name = "lon")
    val lon: String,

    @field:Json(name = "lat")
    val lat: String,



    ) : Parcelable
