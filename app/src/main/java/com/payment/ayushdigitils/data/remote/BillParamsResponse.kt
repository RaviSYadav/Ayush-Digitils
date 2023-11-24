package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize



@Parcelize
@JsonClass(generateAdapter = true)
data class BillParamsResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "parame")
    val parame: List<NetworkBillParamsData> = listOf(),
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkBillParamsData(
    @field:Json(name = "paramname") val paramname: String,
    @field:Json(name = "maxlength") val maxlength: String,
    @field:Json(name = "minlength") val minlength: String,
    @field:Json(name = "ismandatory") val ismandatory: String,
    @field:Json(name = "fieldtype") val fieldtype: String,
    @field:Json(name = "regex") val regex: String,
    @field:Json(name = "fieldInputValue") var fieldInputValue: String? = null,
) : Parcelable