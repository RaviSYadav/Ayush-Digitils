package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PaySprintDmtVerifyResponse(
    @field:Json(name = "statuscode") val statuscode: String,
    @field:Json(name = "message") val message: String,

    @field:Json(name = "data")
    val data: NetworkPaySprintDmtVerifyData? = null,

    @field:Json(name = "benedata")
    val benedata: List<NetworkPaySprintBeneficiaryData> = listOf(),

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPaySprintDmtVerifyData(

    @field:Json(name = "stateresp")
    val stateresp: String?,

    @field:Json(name = "fname")
    val fname: String?,

    @field:Json(name = "lname")
    val lname: String?,

    @field:Json(name = "mobile")
    val mobile: String?,

    @field:Json(name = "bank1_limit")
    val bank1_limit: Int?,

    ) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPaySprintBeneficiaryData(

    @field:Json(name = "bene_id")
    val bene_id: String,

    @field:Json(name = "bankid")
    val bankid: String,

    @field:Json(name = "bankname")
    val bankname: String,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "accno")
    val accno: String,

    @field:Json(name = "ifsc")
    val ifsc: String,

    @field:Json(name = "verified")
    val verified: String,

    @field:Json(name = "banktype")
    val banktype: String,

    ) : Parcelable
