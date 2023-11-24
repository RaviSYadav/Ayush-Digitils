package com.payment.ayushdigitils.ui.fragments.aadhar2fa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.Aadhaar2FaResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class Aadhaar2FaViewModel : BaseViewModel() {

    val request2FBody = MutableLiveData<RequestBody>()



    val getAadhaar2FaResponse: LiveData<Resource<Aadhaar2FaResponse>> = request2FBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestAadhaar2Fa(it)
        }
    }
}