package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FundBankResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "message") val message: String,
    @field:Json(name = "data") val data: FundBankData?
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class FundBankData(
    @field:Json(name = "banks") val banks: List<FundBankList>? = listOf(),
    @field:Json(name = "paymodes") val paymodes: List<FundPayModes>? = listOf(),

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class FundBankList(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "ifsc") val ifsc: String,
    @field:Json(name = "account") val account: String,
    @field:Json(name = "username") val username: String,
    @field:Json(name = "id") val id: String,

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class FundPayModes(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "status") val status: String,

    ) : Parcelable
