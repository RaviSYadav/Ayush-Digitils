package com.payment.ayushdigitils.persistence

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Created by Rinav Gangar <rinav.dev> on 3/4/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
class Prefs(private val prefs: SharedPreferences) {

    companion object {
        private const val PREF_KEY_IS_ANONYMOUS = "PREF_KEY_IS_ANONYMOUS"
        private const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"
        private const val PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME"
        private const val PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL"
        private const val PREF_KEY_USER_CONTACT = "PREF_KEY_USER_CONTACT"
        private const val PREF_KEY_IS_FORCE_UPDATE_ENABLE = "PREF_KEY_IS_FORCE_UPDATE_ENABLE"
        private const val PREF_KEY_APP_VERSION_CODE = "PREF_KEY_APP_VERSION_CODE"
        private const val PREF_KEY_IS_AEPS_SDK_ENABLE = "PREF_KEY_IS_AEPS_SDK_ENABLE"
        private const val PREF_KEY_NEWS = "PREF_KEY_NEWS"
        private const val PREF_KEY_OTP_VERIFY = "PREF_KEY_OTP_VERIFY"
        private const val PREF_KEY_MAIN_WALLET = "PREF_KEY_MAIN_WALLET"
        private const val PREF_KEY_NSDL_WALLET = "PREF_KEY_NSDL_WALLET"
        private const val PREF_KEY_STATUS = "PREF_KEY_STATUS"
        private const val PREF_KEY_MICRO_ATM_BALANCE = "PREF_KEY_MICRO_ATM_BALANCE"
        private const val PREF_KEY_TOKEN_AMOUNT = "PREF_KEY_TOKEN_AMOUNT"
        private const val PREF_KEY_UTI_ID = "PREF_KEY_UTI_ID"
        private const val PREF_KEY_STATE = "PREF_KEY_STATE"
        private const val PREF_KEY_APES_BALANCE = "PREF_KEY_APES_BALANCE"
        private const val PREF_KEY_LOCKED_AMOUNT = "PREF_KEY_LOCKED_AMOUNT"
        private const val PREF_KEY_ROLE_ID = "PREF_KEY_ROLE_ID"
        private const val PREF_KEY_PARENT_ID = "PREF_KEY_PARENT_ID"
        private const val PREF_KEY_COMPANY_ID = "PREF_KEY_COMPANY_ID"
        private const val PREF_KEY_ADDRESS = "PREF_KEY_ADDRESS"
        private const val PREF_KEY_SHOP_NAME = "PREF_KEY_SHOP_NAME"
        private const val PREF_KEY_GSTIN = "PREF_KEY_GSTIN"
        private const val PREF_KEY_CITY = "PREF_KEY_CITY"
        private const val PREF_KEY_PINCODE = "PREF_KEY_PINCODE"
        private const val PREF_KEY_PAN_CARD = "PREF_KEY_PAN_CARD"
        private const val PREF_KEY_AADHAR_CARD= "PREF_KEY_AADHAR_CARD"
        private const val PREF_KEY_KYC = "PREF_KEY_KYC"
        private const val PREF_KEY_ACCOUNT = "PREF_KEY_ACCOUNT"
        private const val PREF_KEY_BANK = "PREF_KEY_BANK"
        private const val PREF_KEY_IFSC = "PREF_KEY_IFSC"
        private const val PREF_KEY_APP_TOKEN = "PREF_KEY_APP_TOKEN"
        private const val PREF_KEY_BC_ID = "PREF_KEY_BC_ID"
        private const val PREF_KEY_PSA_ID = "PREF_KEY_PSA_ID"
        private const val PREF_KEY_BBPS_ID = "PREF_KEY_BBPS_ID"
        private const val PREF_KEY_ROLE_NAME = "PREF_KEY_ROLE_NAME"
        private const val PREF_KEY_ROLE_SLUG = "PREF_KEY_ROLE_SLUG"
        private const val PREF_KEY_COMPANY_NAME = "PREF_KEY_COMPANY_NAME"
        private const val PREF_KEY_WEBSITE = "PREF_KEY_WEBSITE"
        private const val PREF_KEY_LOGO = "PREF_KEY_LOGO"

        private const val PREF_KEY_LOGIN_ID = "PREF_KEY_LOGIN_ID"





        private const val PREF_KEY_PAYSPRINT_ONBOARD = "PREF_KEY_PAYSPRINT_ONBOARD"
        private const val PREF_KEY_UPIMERCHANT = "PREF_KEY_UPIMERCHANT"
        private const val PREF_KEY_PKEY = "PREF_KEY_PKEY"
        private const val PREF_KEY_API_KEY = "PREF_KEY_API_KEY"
        private const val PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE"
        private const val PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE"
        private const val PREF_KEY_MCODE = "PREF_KEY_MCODE"
        private const val PREF_KEY_IS_F_AEPS_AUTH = "PREF_KEY_IS_F_AEPS_AUTH"
        private const val PREF_CURRENT_SELECTED_DEVICE = "PREF_CURRENT_SELECTED_DEVICE"
        const val PREF_CURRENT_SELECTED_DEVICE_INDEX = "PREF_CURRENT_SELECTED_DEVICE_INDEX"

    }


    fun setIsAnonymous(data: Boolean) {
        prefs.edit { putBoolean(PREF_KEY_IS_ANONYMOUS, data) }
    }

    fun getIsAnonymous(): Boolean =
        prefs.getBoolean(PREF_KEY_IS_ANONYMOUS, true)


    fun setUserId(data: Int) {
        prefs.edit { putInt(PREF_KEY_USER_ID, data) }
    }

    fun getUserId(): Int =
        prefs.getInt(PREF_KEY_USER_ID, 0)


    fun setUserName(data: String) {
        prefs.edit { putString(PREF_KEY_USER_NAME, data) }
    }

    fun getUserName(): String =
        prefs.getString(PREF_KEY_USER_NAME, "") ?: ""


    fun setUserEmail(data: String) {
        prefs.edit { putString(PREF_KEY_USER_EMAIL, data) }
    }

    fun getUserEmail(): String =
        prefs.getString(PREF_KEY_USER_EMAIL, "") ?: ""


    fun setUserContact(data: String) {
        prefs.edit { putString(PREF_KEY_USER_CONTACT, data) }
    }

    fun getUserContact(): String =
        prefs.getString(PREF_KEY_USER_CONTACT, "") ?: ""


    fun setForceUpdate(data: String) {
        prefs.edit { putString(PREF_KEY_IS_FORCE_UPDATE_ENABLE, data) }
    }

    fun getForceUpdate(): String =
        prefs.getString(PREF_KEY_IS_FORCE_UPDATE_ENABLE, "") ?: ""


    fun setAppVersionCode(data: String) {
        prefs.edit { putString(PREF_KEY_APP_VERSION_CODE, data) }
    }

    fun getAppVersionCode(): String =
        prefs.getString(PREF_KEY_APP_VERSION_CODE, "") ?: ""


    fun setIsAepsSdkEnable(data: String) {
        prefs.edit { putString(PREF_KEY_IS_AEPS_SDK_ENABLE, data) }
    }

    fun getIsAepsSdkEnable(): String =
        prefs.getString(PREF_KEY_IS_AEPS_SDK_ENABLE, "no") ?: ""


    fun setNews(data: String) {
        prefs.edit { putString(PREF_KEY_NEWS, data) }
    }

    fun getNews(): String =
        prefs.getString(PREF_KEY_NEWS, "") ?: ""


    fun setOtpVerify(data: String) {
        prefs.edit { putString(PREF_KEY_OTP_VERIFY, data) }
    }

    fun getOtpVerify(): String =
        prefs.getString(PREF_KEY_OTP_VERIFY, "") ?: ""


    fun setMainWallet(data: String) {
        prefs.edit { putString(PREF_KEY_MAIN_WALLET, data) }
    }

    fun getMainWallet(): String =
        prefs.getString(PREF_KEY_MAIN_WALLET, "")?:""


    fun setNsdlWallet(data: Int) {
        prefs.edit { putInt(PREF_KEY_NSDL_WALLET, data) }
    }

    fun getNsdlWallet(): Int =
        prefs.getInt(PREF_KEY_NSDL_WALLET, 0)


    fun setStatus(data: String) {
        prefs.edit { putString(PREF_KEY_STATUS, data) }
    }

    fun getStatus(): String =
        prefs.getString(PREF_KEY_STATUS, "") ?: ""


    fun setMicroAtmBalance(data: Int) {
        prefs.edit { putInt(PREF_KEY_MICRO_ATM_BALANCE, data) }
    }

    fun getMicroAtmBalance(): Int =
        prefs.getInt(PREF_KEY_MICRO_ATM_BALANCE, 0)


    fun setTokenAmount(data: String) {
        prefs.edit { putString(PREF_KEY_TOKEN_AMOUNT, data) }
    }

    fun getTokenAmount(): String =
        prefs.getString(PREF_KEY_TOKEN_AMOUNT, "") ?: ""


    fun setUtiID(data: String) {
        prefs.edit { putString(PREF_KEY_UTI_ID, data) }
    }

    fun getUtiId(): String =
        prefs.getString(PREF_KEY_UTI_ID, "") ?: ""


    fun setState(data: String) {
        prefs.edit { putString(PREF_KEY_STATE, data) }
    }

    fun getState(): String =
        prefs.getString(PREF_KEY_STATE, "") ?: ""


    fun setAepsBalance(data: String) {
        prefs.edit { putString(PREF_KEY_APES_BALANCE, data) }
    }

    fun getAepsBalance(): String =
        prefs.getString(PREF_KEY_APES_BALANCE, "") ?: ""


    fun setLockedAmount(data: Int) {
        prefs.edit { putInt(PREF_KEY_LOCKED_AMOUNT, data) }
    }

    fun getLockedAmount(): Int =
        prefs.getInt(PREF_KEY_LOCKED_AMOUNT, 0)


    fun setRoleId(data: String) {
        prefs.edit { putString(PREF_KEY_ROLE_ID, data) }
    }

    fun getRoleId(): String =
        prefs.getString(PREF_KEY_ROLE_ID, "") ?: ""


    fun setParentId(data: Int) {
        prefs.edit { putInt(PREF_KEY_PARENT_ID, data) }
    }

    fun getParentId(): Int =
        prefs.getInt(PREF_KEY_PARENT_ID, 0)


    fun setCompanyId(data: Int) {
        prefs.edit { putInt(PREF_KEY_COMPANY_ID, data) }
    }

    fun getCompanyId(): Int =
        prefs.getInt(PREF_KEY_COMPANY_ID, 0)


    fun setAddress(data: String) {
        prefs.edit { putString(PREF_KEY_ADDRESS, data) }
    }

    fun getAddress(): String =
        prefs.getString(PREF_KEY_ADDRESS, "") ?: ""


    fun setShopName(data: String) {
        prefs.edit { putString(PREF_KEY_SHOP_NAME, data) }
    }

    fun getShopName(): String =
        prefs.getString(PREF_KEY_SHOP_NAME, "") ?: ""


    fun setGstIN(data: Int) {
        prefs.edit { putInt(PREF_KEY_GSTIN, data) }
    }

    fun getGstIn(): Int =
        prefs.getInt(PREF_KEY_GSTIN, 0)


    fun setCity(data: String) {
        prefs.edit { putString(PREF_KEY_CITY, data) }
    }

    fun getCity(): String = prefs.getString(PREF_KEY_CITY, "") ?: ""


    fun setPincode(data: String) {
        prefs.edit { putString(PREF_KEY_PINCODE, data) }
    }

    fun getPincode(): String = prefs.getString(PREF_KEY_PINCODE, "") ?: ""


    fun setPanCard(data: String) {
        prefs.edit { putString(PREF_KEY_PAN_CARD, data) }
    }

    fun getPanCard(): String = prefs.getString(PREF_KEY_PAN_CARD, "") ?: ""


    fun setAadharCard(data: String) {
        prefs.edit { putString(PREF_KEY_AADHAR_CARD, data) }
    }

    fun getAadharCard(): String = prefs.getString(PREF_KEY_AADHAR_CARD, "") ?: ""


    fun setKyc(data: String) {
        prefs.edit { putString(PREF_KEY_KYC, data) }
    }

    fun getKyc(): String = prefs.getString(PREF_KEY_KYC, "") ?: ""


    fun setAccount(data: String) {
        prefs.edit { putString(PREF_KEY_ACCOUNT, data) }
    }

    fun getAccount(): String = prefs.getString(PREF_KEY_ACCOUNT, "") ?: ""


    fun setBank(data: String) {
        prefs.edit { putString(PREF_KEY_BANK, data) }
    }

    fun getBank(): String = prefs.getString(PREF_KEY_BANK, "") ?: ""


    fun setIFSC(data: String) {
        prefs.edit { putString(PREF_KEY_IFSC, data) }
    }

    fun getIFSC(): String = prefs.getString(PREF_KEY_IFSC, "") ?: ""


    fun setAppToken(data: String) {
        prefs.edit { putString(PREF_KEY_APP_TOKEN, data) }
    }

    fun getAppToken(): String = prefs.getString(PREF_KEY_APP_TOKEN, "") ?: ""


    fun setBcId(data: String) {
        prefs.edit { putString(PREF_KEY_BC_ID, data) }
    }

    fun getBcID(): String = prefs.getString(PREF_KEY_BC_ID, "") ?: ""


    fun setPsaID(data: String) {
        prefs.edit { putString(PREF_KEY_PSA_ID, data) }
    }

    fun getPsaID(): String = prefs.getString(PREF_KEY_PSA_ID, "") ?: ""


    fun setBbpsID(data: String) {
        prefs.edit { putString(PREF_KEY_BBPS_ID, data) }
    }

    fun getBbpsID(): String = prefs.getString(PREF_KEY_BBPS_ID, "") ?: ""


    fun setLoginID(data: String) {
        prefs.edit { putString(PREF_KEY_LOGIN_ID, data) }
    }

    fun getLoginID(): String = prefs.getString(PREF_KEY_LOGIN_ID, "") ?: ""


    fun setRoleName(data: String) {
        prefs.edit { putString(PREF_KEY_ROLE_NAME, data) }
    }

    fun getRoleName(): String = prefs.getString(PREF_KEY_ROLE_NAME, "") ?: ""


    fun setRoleSlug(data: String) {
        prefs.edit { putString(PREF_KEY_ROLE_SLUG, data) }
    }

    fun getRoleSlug(): String = prefs.getString(PREF_KEY_ROLE_SLUG, "") ?: ""


    fun setCompanyName(data: String) {
        prefs.edit { putString(PREF_KEY_COMPANY_NAME, data) }
    }

    fun getCompanyName(): String = prefs.getString(PREF_KEY_COMPANY_NAME, "") ?: ""


    fun setWebsite(data: String) {
        prefs.edit { putString(PREF_KEY_WEBSITE, data) }
    }

    fun getWebsite(): String = prefs.getString(PREF_KEY_WEBSITE, "") ?: ""

    fun setLogo(data: String) {
        prefs.edit { putString(PREF_KEY_LOGO, data) }
    }

    fun getLogo(): String = prefs.getString(PREF_KEY_LOGO, "") ?: ""


    fun setPaySprintBoard(data: String) {
        prefs.edit { putString(PREF_KEY_PAYSPRINT_ONBOARD, data) }
    }

    fun getPaySprintBoard(): String = prefs.getString(PREF_KEY_PAYSPRINT_ONBOARD, "") ?: ""


    fun setUpiMerchant(data: String) {
        prefs.edit { putString(PREF_KEY_UPIMERCHANT, data) }
    }

    fun getUpiMerchant(): String = prefs.getString(PREF_KEY_UPIMERCHANT, "") ?: ""


    fun setPkey(data: String) {
        prefs.edit { putString(PREF_KEY_PKEY, data) }
    }

    fun getPkey(): String = prefs.getString(PREF_KEY_PKEY, "") ?: ""


    fun setApiKey(data: String) {
        prefs.edit { putString(PREF_KEY_API_KEY, data) }
    }

    fun getApiKey(): String = prefs.getString(PREF_KEY_API_KEY, "") ?: ""


    fun setLatitude(data: String) {
        prefs.edit { putString(PREF_KEY_LATITUDE, data) }
    }

    fun getLatitude(): String = prefs.getString(PREF_KEY_LATITUDE, "") ?: ""

    fun setLongitude(data: String) {
        prefs.edit { putString(PREF_KEY_LONGITUDE, data) }
    }

    fun getLongitude(): String = prefs.getString(PREF_KEY_LONGITUDE, "") ?: ""

    fun setMCode(data: String) {
        prefs.edit { putString(PREF_KEY_MCODE, data) }
    }

    fun getMCode(): String = prefs.getString(PREF_KEY_MCODE, "") ?: ""

    fun setFAepsAuth(data: String) {
        prefs.edit { putString(PREF_KEY_IS_F_AEPS_AUTH, data) }
    }

    fun getFAepsAuth(): String = prefs.getString(PREF_KEY_IS_F_AEPS_AUTH, "") ?: ""


    fun setSelectedDevice(data: String) {
        prefs.edit { putString(PREF_CURRENT_SELECTED_DEVICE, data) }
    }
    fun getSelectedDevice(): String = prefs.getString(PREF_CURRENT_SELECTED_DEVICE, "") ?: ""

    fun setSelectedDeviceIndex(data: String) {
        prefs.edit { putString(PREF_CURRENT_SELECTED_DEVICE_INDEX, data) }
    }
    fun getSelectedDeviceIndex(): String = prefs.getString(PREF_CURRENT_SELECTED_DEVICE_INDEX, "0") ?: "0"



    fun clear() {
        prefs.edit().clear().apply()
    }

}