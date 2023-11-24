package com.payment.ayushdigitils.ui.fragments.dmt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentDmtBottomBinding
import com.payment.ayushdigitils.persistence.Prefs
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.payment.ayushdigitils.ex.manageClick
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class DMTBottomFragment : BottomSheetDialogFragment(R.layout.fragment_dmt_bottom) {
    private var _binding: FragmentDmtBottomBinding? = null
    private val binding get() = _binding!!


    private val args: DMTBottomFragmentArgs by navArgs()
    private val viewModel by viewModel<DMTVerifyViewModel>()
    val prefs: Prefs by inject()
    private var stateresp = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDmtBottomBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnProceed.setOnClickListener {

            if (isValidate()) {
                val jsonObject = JSONObject().apply {
                    put("user_id", prefs.getUserId())
                    put("apptoken", prefs.getAppToken())
                    put("mobile", binding.etMobile.text.toString())
                    put("fname", binding.etFirstName.text.toString())
                    put("lname", binding.etLastName.text.toString())
                    put("otp", binding.etOtp.text.toString())
                    put("stateresp", stateresp)
                    put("type", "registration")
                }
                viewModel.dmtRegistrationBody.value = jsonObject.toString()
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            }
        }
        observer()

        binding.etMobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 9) {
                    dmtVerify()
                } else {
                    visibilityIcon(View.GONE)
                }
            }

        })
    }
    private fun dmtVerify(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", binding.etMobile.text.toString())
            put("type", "verification")
        }
        viewModel.dmtVerifyBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            binding.btnProceed.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getDmtVerifyResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        when (data.statuscode) {
                            "TXN" -> {
                                data.data?.let {txnVerifyDat->

                                    findNavController().navigate(DMTBottomFragmentDirections.actionDMTBottomFragmentToBeneficiaryFragment(txnVerifyDat))
                                }


                            }
                            "RNF" -> {
                                data.data?.let { verifyData ->
                                    stateresp = verifyData.stateresp!!
                                    visibilityIcon(View.VISIBLE)
                                }
                            }
                            else -> {
                                viewModel.toastMsg.value = data.message
                            }
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "remitter ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
        viewModel.getDmtRegistrationResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("dmt registration Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {

                            dmtVerify()

                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "Add remettier ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    private fun visibilityIcon(visibility: Int) {

        binding.btnProceed.visibility = visibility
        binding.llNewDmt.visibility = visibility

    }

    private fun isValidate():Boolean{
        if (binding.etMobile.text.toString().isEmpty()){
            binding.etMobile.error = "Please enter appropriate mobile number"
            return false
        } else if (binding.etFirstName.text.toString().isEmpty()){
            binding.etMobile.error = "Please enter appropriate first number"
            return false
        }else if (binding.etOtp.text.toString().isEmpty()){
            binding.etMobile.error = "Please enter appropriate OTP"
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}