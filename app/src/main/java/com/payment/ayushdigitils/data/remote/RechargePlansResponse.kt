package com.payment.ayushdigitils.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize



@JsonClass(generateAdapter = true)
data class RechargePlansResponse(
    @field:Json(name = "statuscode") val statuscode: String?,
    @field:Json(name = "message") val message: String?,
    @field:Json(name = "data")val data: Map<String, List<NetworkRechargePlansData>>?
)


@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkRechargePlansData(
    @field:Json(name = "rs") val rs: String?,
    @field:Json(name = "validity") val validity: String?,
    @field:Json(name = "desc") val desc: String?,
): Parcelable