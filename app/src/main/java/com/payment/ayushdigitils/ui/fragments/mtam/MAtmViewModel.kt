package com.payment.ayushdigitils.ui.fragments.mtam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.MAtmInitiateResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class MAtmViewModel : BaseViewModel() {
    val paySprintInitBody = MutableLiveData<RequestBody>()
    val securePayInitBody = MutableLiveData<RequestBody>()



    val getPaySprintMAtmInitiateResponse: LiveData<Resource<MAtmInitiateResponse>> = paySprintInitBody.switchMap {
        if (it == null) {
           securePayInitBody.switchMap {it1->
               if (it1 == null) {
                   AbsentLiveData.create()
               } else {
                   showLoader.value = true
                   aawazRepository.requestSecureMAtmInitiate(it1)
               }
           }
        } else {
            showLoader.value = true
            aawazRepository.requestPaySprintMAtmInitiate(it)
        }
    }
    val getSecureMAtmInitiateResponse: LiveData<Resource<MAtmInitiateResponse>> = securePayInitBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestSecureMAtmInitiate(it)
        }
    }
}