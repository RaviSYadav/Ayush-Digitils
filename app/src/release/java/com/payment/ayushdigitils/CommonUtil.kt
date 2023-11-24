package com.payment.ayushdigitils

import android.content.Context
import androidx.annotation.NonNull
import com.aawaz.analytics.v2.Event
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * Created by Rinav Gangar <rinav.dev> on 19/9/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
class CommonUtil {


    companion object {

        const val KEY_REMOTE_CONFIG_LANG = "lang_pr"
        const val KEY_FIRESTORE_SETTINGS = "settings_pr"

        private val builder = OkHttpClient.Builder()

        fun getOkHttpClientBuilder(): OkHttpClient.Builder {
            return builder
        }

        fun postRemoteLog(@NonNull event: Event) {
            // no-op method, this method should not implement any thing here
        }

        fun setupRemoteDebugger(applicationContext: Context) {
            // no-op method, this method should not implement any thing here
            Timber.d("Remote debugger no-op...")
        }
    }
}
