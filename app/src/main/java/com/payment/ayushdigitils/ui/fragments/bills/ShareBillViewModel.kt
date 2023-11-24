package com.payment.ayushdigitils.ui.fragments.bills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payment.ayushdigitils.data.remote.NetworkBillParamsData




class ShareBillViewModel : ViewModel() {
    private val dataList = MutableLiveData<MutableList<NetworkBillParamsData>>()
    fun setDataList(list: MutableList<NetworkBillParamsData>) {
        dataList.value = list
    }

    fun getDataList(): LiveData<MutableList<NetworkBillParamsData>> {
        return dataList
    }
}