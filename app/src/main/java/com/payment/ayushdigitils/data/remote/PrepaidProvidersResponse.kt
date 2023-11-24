package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class PrepaidProvidersResponse(
    @field:Json(name = "statuscode") val statuscode: String,
    @field:Json(name = "message") val message: String,
    @field:Json(name = "data")
    val data: List<NetworkPrepaidProvidersData> = listOf(),
    ) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPrepaidProvidersData(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    ) : Parcelable
