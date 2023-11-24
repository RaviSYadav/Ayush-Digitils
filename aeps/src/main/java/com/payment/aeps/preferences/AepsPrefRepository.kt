package com.payment.aeps.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AepsPrefRepository(val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)


    fun setUserId(msg: String) {
        PREF_USER_ID.put(msg)
    }
    fun getUserId() = PREF_USER_ID.getString()

    fun setAppToken(msg: String) {
        PREF_APP_TOKEN.put(msg)
    }
    fun getAppToken() = PREF_APP_TOKEN.getString()

    fun setAepsType(msg: String) {
        PREF_AEPS_TYPE.put(msg)
    }
    fun getAepsType() = PREF_AEPS_TYPE.getString()

    fun setSelectedDevice(msg: String) {
        PREF_CURRENT_SELECTED_DEVICE.put(msg)
    }
    fun getSelectedDevice() = PREF_CURRENT_SELECTED_DEVICE.getString()?:null

    fun setSelectedDeviceIndex(msg: String) {
        PREF_CURRENT_SELECTED_DEVICE_INDEX.put(msg)
    }
    fun getSelectedDeviceIndex() = PREF_CURRENT_SELECTED_DEVICE_INDEX.getString()


    companion object{
        const val PREFERENCE_NAME = "MY_APP_PREF"

        const val PREF_LOGGED_IN = "PREF_LOGGED_IN"
        const val PREF_IS_LANGUAGE_SELECTED = "PREF_IS_LANGUAGE_SELECTED"

        const val PREF_CONTACT_EMAIL = "PREF_CONTACT_EMAIL"
        const val PREF_USER_ID = "PREF_USER_ID"
        const val PREF_APP_TOKEN = "PREF_APP_TOKEN"
        const val PREF_AEPS_TYPE = "PREF_AEPS_TYPE"
        const val PREF_CURRENT_SELECTED_DEVICE = "PREF_CURRENT_SELECTED_DEVICE"
        const val PREF_CURRENT_SELECTED_DEVICE_INDEX = "PREF_CURRENT_SELECTED_DEVICE_INDEX"
        const val PREF_MINIMUM_APP_VERSION = "PREF_MINIMUM_APP_VERSION"
    }

}