package com.payment.ayushdigitils.ui.fragments.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.PaySprintDmtBanksResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource

class BanksViewModel : BaseViewModel() {

    val bankType = MutableLiveData<String>()



    init {
        bankType.value = "getbank"
    }

    val getBankResponse: LiveData<Resource<PaySprintDmtBanksResponse>> = bankType.switchMap {
        if (bankType == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestPaySprintDmtGetBank(it)
        }
    }
}