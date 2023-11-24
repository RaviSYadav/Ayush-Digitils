package com.payment.ayushdigitils

import android.content.Context
import androidx.annotation.NonNull
import okhttp3.OkHttpClient
import timber.log.Timber
import zerobranch.androidremotedebugger.AndroidRemoteDebugger
import zerobranch.androidremotedebugger.logging.NetLoggingInterceptor

/**
 * Created by Rinav Gangar <rinav.dev> on 19/9/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
class CommonUtil {

    companion object {

        const val KEY_REMOTE_CONFIG_LANG = "lang_st"
        const val KEY_FIRESTORE_SETTINGS = "settings_st"

        private val builder = OkHttpClient.Builder()

        fun getOkHttpClientBuilder(): OkHttpClient.Builder {
            if (BuildConfig.DEBUG) {
//                if (BuildConfig.FLAVOR_environment == "dev") {
                    builder.addInterceptor(NetLoggingInterceptor())
//                }
            }
            return builder
        }


//        fun setupRemoteDebugger(applicationContext: Context) {
//            if (BuildConfig.DEBUG) {
//                if (BuildConfig.FLAVOR_environment == "dev") {
//                    Timber.d("Attached remote debugger")
//
//                    val builder = AndroidRemoteDebugger.Builder(applicationContext)
//                        .enabled(BuildConfig.DEBUG)
//                        .port(9090)
////                           /*Add new  for sdk 31+*/
//                        .disableInternalLogging()
//                        .enableDuplicateLogging()
//                        .disableNotifications()
//                        .excludeUncaughtException()
//                        .build()
//
//                    AndroidRemoteDebugger.init(builder)
//
//                }
//            }
//        }
    }
}