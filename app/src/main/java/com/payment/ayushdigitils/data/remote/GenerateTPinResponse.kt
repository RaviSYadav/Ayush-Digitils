package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GenerateTPinResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "message") val message: String?,
) : Parcelable
