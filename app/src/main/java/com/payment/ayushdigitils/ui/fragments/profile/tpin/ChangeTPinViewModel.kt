package com.payment.ayushdigitils.ui.fragments.profile.tpin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody

class ChangeTPinViewModel : BaseViewModel() {


    val requestGetOtp = MutableLiveData<RequestBody>()
    val requestGenerateTPin = MutableLiveData<RequestBody>()



    val getOtpResponse = requestGetOtp.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestGetTOtp(it)
        }
    }
    val getGenerateTPinResponse = requestGenerateTPin.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestGenerateTPin(it)
        }
    }

}