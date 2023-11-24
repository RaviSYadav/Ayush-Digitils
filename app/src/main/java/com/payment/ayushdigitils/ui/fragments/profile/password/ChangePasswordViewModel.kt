package com.payment.ayushdigitils.ui.fragments.profile.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody

class ChangePasswordViewModel : BaseViewModel() {


    val requestChangePasswords = MutableLiveData<RequestBody>()



    val getChangePasswordResponse = requestChangePasswords.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestChangePassword(it)
        }
    }

}