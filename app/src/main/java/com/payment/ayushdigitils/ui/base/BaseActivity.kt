package com.payment.ayushdigitils.ui.base


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.payment.ayushdigitils.persistence.Prefs
import com.zplesac.connectionbuddy.ConnectionBuddy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Rinav Gangar <rinav.dev> on 31/5/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
abstract class BaseActivity(layout:Int) : AppCompatActivity(layout), CoroutineScope {

    val prefs: Prefs by inject()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (savedInstanceState != null) {
            //ConnectionBuddyCache.clearInternetConnection(this)
            ConnectionBuddy.getInstance()
                .configuration.networkEventsCache.clearLastNetworkState(this)
        }
    }


    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}