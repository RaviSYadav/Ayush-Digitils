package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class FundUserBankResponse(
    @field:Json(name = "statuscode") val statuscode: String,
    @field:Json(name = "data") val data:List<FundUserBankData> = listOf(),
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class FundUserBankData(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "user_id") val user_id: String,
    @field:Json(name = "account") val account: String,
    @field:Json(name = "ifsc") val ifsc: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "bankname") val bankname: String,

    ) : Parcelable