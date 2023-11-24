package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class PaySprintDmtBanksResponse(
    @field:Json(name = "statuscode") val statuscode: String,
    @field:Json(name = "message") val message: String,
    @field:Json(name = "data")
    val data: NetworkPaySprintDmtResponseData,


    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPaySprintDmtResponseData(

    @field:Json(name = "banks")
    val banks: List<NetworkPaySprintDmtBanksData> = listOf(),

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPaySprintDmtBanksData(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "bankid")
    val bankid: String,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "ifsc")
    val ifsc: String,

    @field:Json(name = "url")
    val url: String,

    @field:Json(name = "pennydrop")
    val pennydrop: String,

    @field:Json(name = "mode")
    val mode: String

    ) : Parcelable