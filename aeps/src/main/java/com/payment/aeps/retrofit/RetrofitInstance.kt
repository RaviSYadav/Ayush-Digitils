package com.payment.aeps.retrofit

import com.payment.aeps.network.AepsPayApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().apply {
        this.addInterceptor(intercepter)
            // time out setting
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(25,TimeUnit.SECONDS)

    }.build()
    val api: AepsPayApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://login.ayusdigital.co.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AepsPayApiService:: class.java)
    }
}