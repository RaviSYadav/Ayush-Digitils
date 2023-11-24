package com.payment.ayushdigitils.ui.fragments.profile.tpin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentChangeTpinBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import timber.log.Timber


class ChangeTPinFragment : BaseFragment(R.layout.fragment_change_tpin) {

    private var _binding: FragmentChangeTpinBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ChangeTPinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeTpinBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()
    }
    override fun initView() {
    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.lblGetOtp.setOnClickListener {
            requestGetOtp()
        }

        binding.btnProceed.setOnClickListener {
            if (isValidate()){
                requestGenerateTPin()
            }
        }


    }

    private fun requestGetOtp(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", prefs.getUserContact())
        }
        viewModel.requestGetOtp.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun requestGenerateTPin(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", prefs.getUserContact())
            put("tpin", binding.etNewPin.text.toString())
            put("tpin_confirmation", binding.etConfirmPin.text.toString())
            put("otp", binding.etOtp.text.toString())
        }
        viewModel.requestGenerateTPin.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun isValidate(): Boolean {
        if (binding.etOtp.text.isNullOrEmpty()) {
            binding.etOtp.apply {
                error = "Please enter appropriate old password"
                requestFocus()
            }
            return false
        }
        else if (binding.etNewPin.text.isNullOrEmpty() || binding.etNewPin.text.toString().length < 4) {
            binding.etNewPin.apply {
                error = "Please enter appropriate new password"
                requestFocus()
            }
            return  false
        }
        else if (binding.etConfirmPin.text.isNullOrEmpty() || binding.etConfirmPin.text.toString().length < 4) {
            binding.etConfirmPin.apply {
                error = "Please enter appropriate confirm password"
                requestFocus()
            }
            return  false
        }
        else if (binding.etConfirmPin.text.toString() != binding.etNewPin.text.toString()) {
            binding.etConfirmPin.apply {
                error = "enter appropriate confirm password does not match"
                requestFocus()
            }
            return  false
        }
        return true
    }
    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getOtpResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("TPin Otp Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        viewModel.toastMsg.value = data.message
                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "TPin Otp ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })

        viewModel.getGenerateTPinResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("Generate T Pin Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        createAlertDialog(message = data.message?:"")
                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "Generate T Pin ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    private fun createAlertDialog(message:String): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())

        // Set the title and message for the AlertDialog
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->

            findNavController().navigateUp()
            dialog.dismiss()
        }

        return builder.create()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}