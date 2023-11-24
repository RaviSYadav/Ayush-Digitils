package com.payment.ayushdigitils.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import com.payment.ayushdigitils.ui.signin.LoginActivity
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ActivitySignupBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseActivity
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignUpActivity : BaseActivity(R.layout.activity_signup) {
    private lateinit var binding:ActivitySignupBinding

    private val viewModel by viewModel<SignupViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()

        binding.btnSignup.setOnClickListener {
            if (isValidate()){
                signup()
            }
        }
    }

    private fun signup() {
        val jsonObject = JSONObject().apply {
            put("name", binding.etName.text.toString())
            put("mobile", binding.etMobile.text.toString())
            put("email", binding.etEmail.text.toString())
            put("shopname", binding.etShopName.text.toString())
            put("pancard", binding.etPancard.text.toString())
            put("pancard", binding.etAadhar.text.toString())
            put("aadharcard", binding.etAadhar.text.toString())
            put("state", binding.etState.text.toString())
            put("city", binding.etCity.text.toString())
            put("address", binding.etAddress.text.toString())
            put("pincode", binding.etPincode.text.toString())
            put("slug", binding.etSlug.text.toString())
        }

        viewModel.signupBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


    }

    private fun isValidate(): Boolean {
        var check = true
         if (binding.etName.text.toString()
                .isNotEmpty() && !(binding.etName.text.toString().matches("[a-zA-Z ]*".toRegex()))
        ) {
             viewModel.toastMsg.value = "Please enter appropriate first name"
            check = false
//
        } else if (binding.etMobile.text.isNullOrEmpty() && !Patterns.PHONE.matcher(binding.etMobile.text).matches()) {
            viewModel.toastMsg.value = "Please enter appropriate phone number"
            check = false
        }
        else if (binding.etEmail.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Email Id can not be empty"
            check = false
        } else if (binding.etEmail.text.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
             viewModel.toastMsg.value = "Please enter appropriate email id"
            check = false
        } else if (binding.etShopName.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate shop name"
            check = false
        }else if (binding.etShopName.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate shop name"
            check = false
        }else if (binding.etPancard.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate pan card"
            check = false
        }else if (binding.etAadhar.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate aadhar card"
            check = false
        }else if (binding.etState.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate state"
            check = false
        }else if (binding.etCity.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate city"
            check = false
        }else if (binding.etAddress.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate address"
            check = false
        }else if (binding.etPincode.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate pin code"
            check = false
        }else if (binding.etSlug.text.isNullOrEmpty()) {
            viewModel.toastMsg.value = "Please enter appropriate slug"
            check = false
        }
        return check
    }

    private fun observer() {
        viewModel.showLoader.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            binding.btnSignup.manageClick(it)
        })
        viewModel.toastMsg.observe(this, Observer {
            showToast(it)
        })

        viewModel.getSignupResponse.observe(this, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        Timber.d("subscribe : $data")
                        viewModel.toastMsg.value = data.message
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()


                    }
                }

                is Resource.Error -> {
                    Timber.e(it.exception, "subscribe ex:")
                }
            }
        })
    }
}