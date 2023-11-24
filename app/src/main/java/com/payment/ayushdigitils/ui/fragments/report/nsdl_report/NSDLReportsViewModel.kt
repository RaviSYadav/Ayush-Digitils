package com.payment.ayushdigitils.ui.fragments.report.nsdl_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.payment.ayushdigitils.repository.NSDLReportsRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData


class NSDLReportsViewModel(private val repo: NSDLReportsRepository) : BaseViewModel() {

    val userId = MutableLiveData<String>()
    val apptoken = MutableLiveData<String>()
    val searchtext = MutableLiveData<String>()
    val status = MutableLiveData<String>()
    val fromdate = MutableLiveData<String>()
    val todate = MutableLiveData<String>()

    init {
        showLoader.value = true
        userId.value =  prefs.getUserId().toString()
        apptoken.value =  prefs.getAppToken()
    }


    val reportItem = userId.switchMap { slugString ->
        if (slugString == null) {
            AbsentLiveData.create()
        } else {
            apptoken.switchMap {apptoken->
                showLoader.value = true
                repo.fetchNSDLResults(
                    user_id = slugString,
                    apptoken = apptoken,
                    searchtext = searchtext.value,
                    status = status.value,
                    fromdate = fromdate.value,
                    todate = todate.value,
                ).cachedIn(viewModelScope)

            }


                }
            }

            }







