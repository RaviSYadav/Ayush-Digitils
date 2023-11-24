package com.payment.ayushdigitils.di

import android.content.Context
import android.content.SharedPreferences
import com.payment.ayushdigitils.persistence.Prefs
import com.payment.ayushdigitils.utils.C
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Rinav Gangar <rinav.dev> on 21/10/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
val preferencesModule = module {

    single {
        providePreferences(androidContext())
    }

    single {
        Prefs(prefs = get())
    }
}

private fun providePreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(C.PREFS_FILE_NAME, Context.MODE_PRIVATE)