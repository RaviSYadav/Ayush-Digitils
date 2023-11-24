package com.payment.ayushdigitils.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.SignupResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class SignupViewModel : BaseViewModel() {
    val signupBody = MutableLiveData<RequestBody>()



    val getSignupResponse: LiveData<Resource<SignupResponse>> = signupBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestRegister(it)
        }
    }
}