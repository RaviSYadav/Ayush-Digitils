package com.payment.ayushdigitils.ui.signin.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.payment.ayushdigitils.MainActivity
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ActivityOtpBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseActivity
import com.payment.ayushdigitils.ui.signin.LoginViewModel
import com.payment.ayushdigitils.ui.utils.Constanse.configOtpEditText
import com.payment.ayushdigitils.ui.utils.Constanse.makeLinks
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class OTPActivity : BaseActivity(R.layout.activity_otp) {
    private lateinit var binding:ActivityOtpBinding

    private val viewModel by viewModel<LoginViewModel>()

    private lateinit var userId: String
    private lateinit var password: String
    private lateinit var msg: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent.getStringExtra("USER_ID").toString()
        password = intent.getStringExtra("PASSWORD").toString()
        msg = intent.getStringExtra("MSG").toString()
        binding.lblOtpVerification.text = msg
        binding.lblVerificationMessage.text = getString(
            R.string.remitter_full_name,
            "Enter the OTP sent to",
            "+91$userId"
        )
        manageOtp()
        observer()



        binding.btnValidate.setOnClickListener {
            if (getOtp().isNotEmpty()){

                resendLoginJsonJson()
            }else{

                Toast.makeText(this, "Please Enter Otp", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun manageOtp(){
        resendOtp()
        configOtpEditText(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )

    }

    private fun resendOtpJson() {
        val jsonObject = JSONObject().apply {
            put("mobile", userId)
            put("password",password)
            put("otp","resend")
        }

        viewModel.loginBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


    }
    private fun resendLoginJsonJson() {
        val jsonObject = JSONObject().apply {
            put("mobile", userId)
            put("password",password)
            put("otp",getOtp())
        }

        viewModel.loginBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


    }

    private fun getOtp():String{
        Log.v("OTP","==${binding.etOtp1.text.toString()}")
        var otp= ""
       otp =  binding.etOtp1.text.toString()+binding.etOtp2.text.toString()+binding.etOtp3.text.toString()+binding.etOtp4.text.toString()+binding.etOtp5.text.toString()+binding.etOtp6.text.toString()

        return otp

    }

    private fun resendOtp(){
        binding.lblResendOtp.makeLinks(
            Pair("Resend OTP", View.OnClickListener {
                resendOtpJson()
            })
        )
    }

    private fun observer() {
        viewModel.showLoader.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            binding.btnValidate.manageClick(it)
            binding.lblResendOtp.manageClick(it)
        })
        viewModel.toastMsg.observe(this, Observer {
            showToast(it)
        })

        viewModel.getLoginResponse.observe(this, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("otp Res---- : ${it.data}")
                    it.data?.let { data ->

                        Timber.d("otp userdata---- : ${data.userdata}")
                        viewModel.showLoader.value = false
                        if (data.status == "TXN"){
                            data.userdata?.let {userData ->
                                prefs.setUserId(userData.id)
                                prefs.setUserName(userData.name)
                                prefs.setUserEmail(userData.email)
                                prefs.setUserContact(userData.mobile)
                                prefs.setStatus(userData.status)
                                prefs.setAddress(userData.address)
                                prefs.setShopName(userData.shopname)
                                prefs.setCity(userData.city)
                                prefs.setState(userData.state)
                                prefs.setPincode(userData.pincode)
                                prefs.setPanCard(userData.pancard)
                                prefs.setAadharCard(userData.aadharcard)
                                prefs.setAppToken(userData.apptoken)
                                prefs.setIsAnonymous(false)
                            }


                            startActivity(Intent(this, MainActivity::class.java))
                            finish()




                        }
                        else if (data.equals("otp") ||data.equals("OTP")){
                            binding.lblOtpVerification.text = data.message
                        }else{
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.toastMsg.value = "Something went wrong"
                    Timber.e(it.exception, "otp ex:")
                }
            }
        })
    }
}