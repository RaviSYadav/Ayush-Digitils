package com.payment.aeps.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class AepsBankResponse(
    @field:Json(name = "statuscode")
    val statuscode: String,
    @field:Json(name = "message")
    val message: String?="",
    @field:Json(name = "data")
    val data: NetworkAepsData?,

    ) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkAepsData(

    @field:Json(name = "agent")
    val agent: NetworkAepsAgent?,

    @field:Json(name = "bankName")
    val bankName: List<NetworkAepsBank> = listOf(),

) : Parcelable


@kotlinx.parcelize.Parcelize
@JsonClass(generateAdapter = true)
data class NetworkAepsBank(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "activeFlag")
    val activeFlag: String,

    @field:Json(name = "bankName")
    val bankName: String,

    @field:Json(name = "details")
    val details: String,

    @field:Json(name = "iinno")
    val iinno: String,


) : Parcelable


@kotlinx.parcelize.Parcelize
@JsonClass(generateAdapter = true)
data class NetworkAepsAgent(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "merchantLoginId")
    val merchantLoginId: String,

    @field:Json(name = "merchantName")
    val merchantName: String,


) : Parcelable

