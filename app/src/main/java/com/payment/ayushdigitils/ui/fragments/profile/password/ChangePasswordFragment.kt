package com.payment.ayushdigitils.ui.fragments.profile.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.payment.ayushdigitils.NavGraphInvoiceDirections
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentChangePasswordBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceModel
import com.payment.ayushdigitils.ui.utils.Constanse
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Date


class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {

    private var _binding:FragmentChangePasswordBinding ?= null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ChangePasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
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

        binding.btnProceed.setOnClickListener {
            if (isValidate()){

                requestChangePasswords()
            }
        }
    }

    private fun isValidate(): Boolean {
        if (binding.etOldPassword.text.isNullOrEmpty()) {
            binding.etOldPassword.apply {
                error = "Please enter appropriate old password"
                requestFocus()
            }
            return false
        }
        else if (binding.etNewPassword.text.isNullOrEmpty()) {
            binding.etNewPassword.apply {
                error = "Please enter appropriate new password"
                requestFocus()
            }
           return  false
        }
        else if (binding.etConfirmPassword.text.isNullOrEmpty()) {
            binding.etConfirmPassword.apply {
                error = "Please enter appropriate confirm password"
                requestFocus()
            }
           return  false
        }
        else if (binding.etConfirmPassword.text.toString() != binding.etNewPassword.text.toString()) {
            binding.etConfirmPassword.apply {
                error = "enter appropriate confirm password does not match"
                requestFocus()
            }
           return  false
        }
        return true
    }

    private fun requestChangePasswords(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", prefs.getUserContact())
            put("oldpassword", binding.etOldPassword.text.toString())
            put("password", binding.etConfirmPassword.text.toString())
        }
        viewModel.requestChangePasswords.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getChangePasswordResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("change password Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        createAlertDialog(message = data.message?:"")
                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "change password ex:")
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