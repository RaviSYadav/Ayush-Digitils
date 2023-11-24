package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Aadhaar2FaResponse(
    @field:Json(name = "statuscode") val statuscode: String,
    @field:Json(name = "message") val message: String,
): Parcelable
