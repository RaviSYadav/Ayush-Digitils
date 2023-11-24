package com.payment.ayushdigitils.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.payment.ayushdigitils.MainActivity
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ActivityLoginBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseActivity
import com.payment.ayushdigitils.ui.signin.otp.OTPActivity
import com.payment.ayushdigitils.ui.signup.SignUpActivity
import com.payment.ayushdigitils.ui.utils.Constanse.makeLinks
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity : BaseActivity(R.layout.activity_login) {
    private lateinit var binding:ActivityLoginBinding

    private val viewModel by viewModel<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        signup()

        onClick()
    }

    private fun signup(){
        binding.lblSignup.makeLinks(

            Pair("Signup", View.OnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            })
        )
    }

    private fun onClick(){
        binding.btnLogin.setOnClickListener {

            if (isValidate()){
                login()
            }
        }


    }

    private fun login() {
        val jsonObject = JSONObject().apply {
            put("mobile", binding.etUsername.text.toString())
            put("password", binding.etPassword.text.toString())
        }

        viewModel.loginBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


    }
    private fun isValidate(): Boolean {
        var check = true
         if (binding.etUsername.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate username"
            check = false
        }
        else if (binding.etPassword.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate password"
            check = false
        }
        return check
    }

    private fun observer() {
        viewModel.showLoader.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            binding.btnLogin.manageClick(it)

        })
        viewModel.toastMsg.observe(this, Observer {
            showToast(it)
        })

        viewModel.getLoginResponse.observe(this, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("login Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        Timber.d("login userdata---- : ${data.userdata}")
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


                            startActivity(Intent(this,MainActivity::class.java))
                            finish()




                        }else if (data.status == "OTP"){
                            val intent = Intent(Intent(this,OTPActivity::class.java))
                            intent.putExtra("USER_ID",binding.etUsername.text.toString())
                            intent.putExtra("PASSWORD",binding.etPassword.text.toString())
                            intent.putExtra("MSG",data.message)
                            startActivity(intent)

                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.toastMsg.value = "something went wrong"
                    Timber.e(it.exception, "login ex:")
                }
            }
        })
    }


}