package com.payment.ayushdigitils.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.airbnb.epoxy.Carousel
import com.payment.ayushdigitils.BuildConfig
import com.payment.ayushdigitils.di.preferencesModule
import com.payment.ayushdigitils.di.repoModule
import com.payment.ayushdigitils.di.retrofitModule
import com.payment.ayushdigitils.di.viewModelModule
import com.zplesac.connectionbuddy.ConnectionBuddy
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Carousel.setDefaultGlobalSnapHelperFactory(null)
        setupDI()

        setupConnectionBuddy()

    }
    private fun setupDI() {

        startKoin {

            if (BuildConfig.DEBUG) {
                androidLogger(Level.ERROR)
            }

            androidContext(this@MyApp)

            modules(
                listOf(
                    preferencesModule,
                    repoModule,
                    viewModelModule,
                    retrofitModule,
                    preferencesModule
                )
            )
        }
    }

    private fun setupConnectionBuddy() {

        val networkInspectorConfiguration = ConnectionBuddyConfiguration.Builder(this)
            .setNotifyImmediately(false)
            //.notifyOnlyReliableEvents(true)
            .build()

        ConnectionBuddy.getInstance().init(networkInspectorConfiguration)
    }
}