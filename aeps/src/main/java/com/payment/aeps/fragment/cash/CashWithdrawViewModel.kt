package com.payment.aeps.fragment.cash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payment.aeps.remote.AepsBankResponse
import com.payment.aeps.remote.AepsResponse
import com.payment.aeps.retrofit.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CashWithdrawViewModel : ViewModel() {

    private val aepsResponseLiveData = MutableLiveData<AepsResponse>()

    var showLoader = MutableLiveData<Boolean>()

    fun getAepsData(jsonObject: JSONObject) {
        showLoader.value = true
        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        RetrofitInstance.api.requestPaySprintAeps(body).enqueue(object : Callback<AepsResponse> {
            override fun onResponse(call: Call<AepsResponse>, response: Response<AepsResponse>) {

                if (response.body() != null) {
                    showLoader.value =false


                    val aepsResponse: AepsResponse = response.body()!!
                    Log.v("getAepsData","===$aepsResponse")
                    aepsResponseLiveData.value = aepsResponse
                }else{
                    showLoader.value = false
                }

            }

            override fun onFailure(call: Call<AepsResponse>, t: Throwable) {

                showLoader.value =false
                Log.d("getAepsData Er", t.message.toString()+ "=====$call")
            }

        })
    }

    fun observeAepsLiveData() : LiveData<AepsResponse> {
        return aepsResponseLiveData
    }

}