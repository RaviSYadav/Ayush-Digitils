package com.payment.ayushdigitils.ui.fragments.profile.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.LogoutResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class SettingsViewModel : BaseViewModel() {
    val logoutBody = MutableLiveData<RequestBody>()



    val getLogOutResponse: LiveData<Resource<LogoutResponse>> = logoutBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestLogOut(it)
        }
    }

}