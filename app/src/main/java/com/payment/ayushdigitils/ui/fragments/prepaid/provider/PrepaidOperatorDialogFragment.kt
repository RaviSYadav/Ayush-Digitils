package com.payment.ayushdigitils.ui.fragments.prepaid.provider

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPrepaidProvidersData
import com.payment.ayushdigitils.databinding.LayoutDialogPrepaidOperatorBinding
import com.payment.ayushdigitils.ui.fragments.prepaid.PrepaidViewModel
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PrepaidOperatorDialogFragment(listener: OperatorDialogListener, mobileNumber:String, providerType:String) : BottomSheetDialogFragment(R.layout.layout_dialog_prepaid_operator),
    PrepaidProviderController.AdapterCallbacks {

    private var _binding: LayoutDialogPrepaidOperatorBinding? = null
    // This property is only valid between onCreateView and

    // onDestroyView.
    private val binding get() = _binding!!


    private var mBottomSheetListener: OperatorDialogListener?=null
    private var mobileNumber: String?=null
    private var providerType: String?=null

    init {
        this.mBottomSheetListener = listener
        this.mobileNumber = mobileNumber
        this.providerType = providerType
    }

    private val viewModel by viewModel<PrepaidViewModel>()

    private val controller: PrepaidProviderController by lazy { PrepaidProviderController(this) }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutDialogPrepaidOperatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.layout_dialog_prepaid_operator)

        // Set the full-screen style
        val bottomSheetBehavior = dialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 0

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvChooseOperator.setController(controller)
        observer()
        getPrepaidData()
    }

    private fun getPrepaidData(){
        val jsonObject = JSONObject().apply {
            put("number", mobileNumber)
            put("type", providerType)
        }
        viewModel.providerJson.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
//            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getPrepaidProviders.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("bank Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {
                            data.data.let {res->

                                controller.setData(res,providerType)
                            }
                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "recharge confirmation ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        /** attach listener from parent fragment */
        try {
            mBottomSheetListener = context as OperatorDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface OperatorDialogListener {
        fun operatorClick(networkPrepaidProvidersDa: NetworkPrepaidProvidersData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProviderClicked(
        networkPrepaidProvidersDa: NetworkPrepaidProvidersData,
        position: Int
    ) {
        mBottomSheetListener?.operatorClick(networkPrepaidProvidersDa)
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}