package com.payment.ayushdigitils.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.LoginResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class LoginViewModel : BaseViewModel() {
    val loginBody = MutableLiveData<RequestBody>()



    val getLoginResponse: LiveData<Resource<LoginResponse>> = loginBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestLogin(it)
        }
    }
}