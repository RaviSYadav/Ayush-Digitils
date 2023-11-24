package com.payment.aeps.fragment.bank

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payment.aeps.remote.AepsBankResponse
import com.payment.aeps.retrofit.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AepsBankViewModel : ViewModel() {

    private val initiateResponseLiveData = MutableLiveData<AepsBankResponse>()

    fun getBank(jsonObject: JSONObject) {
        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        RetrofitInstance.api.requestSearchResult(body).enqueue(object : Callback<AepsBankResponse> {
            override fun onResponse(call: Call<AepsBankResponse>, response: Response<AepsBankResponse>) {

                if (response.body() != null) {
                    val randomMeal: AepsBankResponse = response.body()!!
                    initiateResponseLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<AepsBankResponse>, t: Throwable) {

                Log.d("Aeps Bank Fragment", t.message.toString())
            }

        })
    }

    fun observeInitiateLiveData() : LiveData<AepsBankResponse> {
        return initiateResponseLiveData
    }

}