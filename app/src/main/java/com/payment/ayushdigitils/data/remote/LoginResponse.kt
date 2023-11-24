package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LoginResponse (

    @field:Json(name = "status") val status: String,
    @field:Json(name = "message") val message: String,

    @field:Json(name = "userdata")
    val userdata: NetworkUserData?= null
): Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkUserData(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "email")
    val email: String,

    @field:Json(name = "mobile")
    val mobile: String,

    @field:Json(name = "status")
    val status: String,

    @field:Json(name = "address")
    val address: String,

    @field:Json(name = "shopname")
    val shopname: String,

    @field:Json(name = "city")
    val city: String,

    @field:Json(name = "state")
    val state: String,

    @field:Json(name = "pincode")
    val pincode: String,

    @field:Json(name = "pancard")
    val pancard: String,

    @field:Json(name = "aadharcard")
    val aadharcard: String,

    @field:Json(name = "apptoken")
    val apptoken: String,

    @field:Json(name = "utiid")
    val utiid: String,

    @field:Json(name = "role")
    val role: NetworkRoleData?,

    @field:Json(name = "company")
    val company: NetworkCompanyData?


) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkRoleData(

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "slug")
    val slug: String

) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkCompanyData(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "companyname")
    val companyname: String,

    @field:Json(name = "website")
    val website: String,

    @field:Json(name = "logo")
    val logo: String,

) : Parcelable