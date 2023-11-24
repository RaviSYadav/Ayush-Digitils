package com.payment.ayushdigitils.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payment.ayushdigitils.persistence.Prefs
import com.payment.ayushdigitils.repository.PayRepository

import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    val aawazRepository: PayRepository by inject(PayRepository::class.java)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()


    val prefs: Prefs by inject(Prefs::class.java)

    var toastMsg = MutableLiveData<String>()
    var showLoader = MutableLiveData<Boolean>()







}