package com.payment.aeps.network




import com.payment.aeps.remote.AepsBankResponse
import com.payment.aeps.remote.AepsResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.Call


interface AepsPayApiService {


    @POST("api/android/raeps/getdata")
    fun requestSearchResult(@Body data: RequestBody): Call<AepsBankResponse>

    @POST("api/android/paysprint/aeps")
    fun requestPaySprintAeps(@Body data: RequestBody): Call<AepsResponse>






}