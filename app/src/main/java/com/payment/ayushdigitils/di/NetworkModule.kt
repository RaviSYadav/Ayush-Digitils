package com.payment.ayushdigitils.di

import android.content.Context
import com.payment.ayushdigitils.BuildConfig
import com.payment.ayushdigitils.CommonUtil
import com.payment.ayushdigitils.app.AppExecutors
import com.payment.ayushdigitils.network.PayApiServices
import com.payment.ayushdigitils.utils.LiveDataCallAdapterFactory
import com.zplesac.connectionbuddy.ConnectionBuddy
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Rinav Gangar <rinav.dev> on 21/10/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
private const val CACHE_STALE_IN_DAYS = 60 * 60 * 24 * 7 // seven day
private const val CACHE_MAX_AGE_IN_SECONDS = 60  // 1 minute

val retrofitModule = module {
    single<PayApiServices>(named("default")) {
        apiServices(okHttpClient = get(qualifier = named("default")))
    }

    single<OkHttpClient>(named("default")) {
        getOkHttpClient(
                cache = get(),
                httpLoggingInterceptor = getHttpLoggingInterceptor(),
        )
    }

    single {
        AppExecutors()
    }

    single {
        provideCacheFile(get())
    }
}

private fun apiServices(okHttpClient: OkHttpClient): PayApiServices {


    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
//        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(PayApiServices::class.java)
}

private fun getOkHttpClient(
    cache: Cache,
    httpLoggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient {
    val builder = CommonUtil.getOkHttpClientBuilder()

    return builder
        .cache(cache)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (ConnectionBuddy.getInstance().hasNetworkConnection())
                request.newBuilder().header(
                    "Cache-Control",
                    "public, max-age=$CACHE_MAX_AGE_IN_SECONDS"
                ).build()
            else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=$CACHE_STALE_IN_DAYS"
                ).build()
            chain.proceed(request)
        }
        .addInterceptor(object : Interceptor {
            private lateinit var response: Response

            override fun intercept(chain: Interceptor.Chain): Response {

                try {

//                    Timber.d("Request :: ${chain.request().url}")

                    val original: Request = chain.request()

//                Timber.d("Request :: ${original.url} ${original.headers.names().toString()}")

                        response = chain.proceed(original)

                    return response
                } catch (ex: Exception) {
                    Timber.e(ex, "Error in network interceptor: ${ex.message}")

                    return chain.proceed(chain.request())
                }
            }
        })
        .build()
}

fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {

    val httpLoggingInterceptor = HttpLoggingInterceptor()

    httpLoggingInterceptor.level =
        if (BuildConfig.DEBUG /*&& BuildConfig.FLAVOR_environment == "dev"*/) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    return httpLoggingInterceptor
}
private fun provideCacheFile(context: Context): Cache {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val cacheFile = File(context.cacheDir, "api-cache")
    return Cache(cacheFile, cacheSize)
}