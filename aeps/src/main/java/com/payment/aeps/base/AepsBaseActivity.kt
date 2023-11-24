package com.payment.aeps.base


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.payment.aeps.preferences.AepsPrefRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Rinav Gangar <rinav.dev> on 31/5/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
abstract class AepsBaseActivity(layout:Int) : AppCompatActivity(layout), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            //ConnectionBuddyCache.clearInternetConnection(this)

        }
    }


    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

}