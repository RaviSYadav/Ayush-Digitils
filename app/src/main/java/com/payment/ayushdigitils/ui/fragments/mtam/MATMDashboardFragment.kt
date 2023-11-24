package com.payment.ayushdigitils.ui.fragments.mtam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentMatmDashboardBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.chip.Chip
import com.paysprint.onboardinglib.activities.HostActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.properties.Delegates


class MATMDashboardFragment : BaseFragment(R.layout.fragment_matm_dashboard) {

    private var _binding:FragmentMatmDashboardBinding? = null
    private val binding get() = _binding!!

    private var _resultCode : Int = 999
    private var _resultCodes : Int = 1111

    private var deviceManufacture by Delegates.notNull<Int>()
    private  var txn_type by Delegates.notNull<String>()

    private val viewModel by viewModel<MAtmViewModel>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMatmDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()

    }
    override fun initView() {
        createDeviceView(devices)
        createTransactionsView(af_transactions)
    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnProceed.setOnClickListener {
            if (validate()){
                if (deviceManufacture == 2 && txn_type == "CW" ){
//                    MATM_INITIATE

                    val jsonObject = JSONObject().apply {

                    }

                    viewModel.securePayInitBody.value = jsonObject.toString()
                        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                }else{



                    val jsonObject = JSONObject().apply {

                    }

                    viewModel.paySprintInitBody.value = jsonObject.toString()
                        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                }

            }
        }

    }
    private fun createDeviceView(devices: List<MatmDevice>) {

        binding.chipGroupDevice.isSingleSelection = true

        if (devices.isNullOrEmpty()) {
            binding.chipGroupDevice.gone()
        } else {

            binding.chipGroupDevice.removeAllViews()
            devices.forEach {
                val chip = layoutInflater.inflate(
                    R.layout.layout_device_chip_choice,
                    binding.chipGroupDevice,
                    false
                ) as Chip

                with(chip) {
                    text = it.device_name
                    tag = "${it.device_manufacture}"
                    chipStrokeWidth = 1.0F
                    setTextAppearance(R.style.ChipText)
                    isCheckable = true
                    binding.chipGroupDevice.addView(this)
                    setOnClickListener{it2->
                        if (isChecked){
                            deviceManufacture = it.device_manufacture

                            if (it2.tag=="3"){
                                createTransactionsView(af_transactions)
                            }else if (it2.tag == "2"){
                                createTransactionsView(mp_transactions)
                            }

                        }

                    }



                }
            }

        }
    }
    private fun createTransactionsView(transactions: List<MatmTransactionsType>) {

        binding.chipGroupTransactions.isSingleSelection = true

        if (transactions.isNullOrEmpty()) {
            binding.chipGroupTransactions.gone()
        } else {

            binding.chipGroupTransactions.removeAllViews()
            transactions.forEach {
                val chip = layoutInflater.inflate(
                    R.layout.layout_device_chip_choice,
                    binding.chipGroupTransactions,
                    false
                ) as Chip

                with(chip) {
                    text = it.transaction
                    tag = it.transactions_type
                    chipStrokeWidth = 1.0F
                    setTextAppearance(R.style.ChipText)
                    isCheckable = true
                    binding.chipGroupTransactions.addView(this)
                    setOnClickListener{

                        if (isChecked){
                            txn_type = "${it.tag}"
                            if (it.tag=="ATMBE" || it.tag=="BE"){
                                binding.lblAmount.gone()
                                binding.viewAmount.gone()
                            }else{
                                binding.lblAmount.visible()
                                binding.viewAmount.visible()
                            }
                        }

                    }



                }
            }

        }
    }
    private fun observer() {
        viewModel.showLoader.observe(this, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.toastMsg.observe(this, Observer {
            showToast(it)
        })

        viewModel.getPaySprintMAtmInitiateResponse.observe(this, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        Timber.d("subscribe : $data")
                       if (data.status.equals("TXN",ignoreCase = true)){
                           data.data?.let {matm->
                           if (deviceManufacture.equals(3)){




//                               matmInitateMethodeFIno(matm.partnerId, matm.apiKey, matm.merchantCode, matm.UserId, matm.bcEmailId, matm.referenceNumber, matm.clientrefid, matm.lon, matm.lat)
                               }else{

                               matmInitateMethode(matm.partnerId, matm.apiKey, matm.merchantCode, matm.UserId, matm.bcEmailId, matm.referenceNumber, matm.clientrefid, matm.lon, matm.lat)
                           }
                           }

                       }else{
                           viewModel.toastMsg.value = data.message
                       }



                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "Matm ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    private fun validate():Boolean{
        if (deviceManufacture == null){
            showToast("Please Select Device")
            return false
        }else if (txn_type == null){
            showToast("Please Select Transaction")
            return false
        }else if (binding.etAmount.text.toString().isEmpty()){
            showToast("Please Enter appropriate Amount")
            return false
        }

        return true
    }


   /* private fun matmInitateMethodeFIno(
        partnerId: String,
        apiKey: String,
        merchantCode: String,
        userId: String,
        bcEmailId: String,
        referenceNumber: String,
        clientrefid: String,
        lon: String,
        lat: String) {

        val intent = Intent(requireContext(), Hostnew::class.java)
        intent.putExtra("partnerId", "" + partnerId)
        intent.putExtra("apiKey", "" + apiKey)
        intent.putExtra("merchantCode", "" + merchantCode)
        intent.putExtra("transactionType", ""+txn_type)
        if(txn_type.equals("ATMBE")){
            intent.putExtra("amount", "0")
        }
        else{intent.putExtra("amount", ""+binding.etAmount.text.toString())
        }
        intent.putExtra("remarks", "Test Transaction")
        intent.putExtra("mobileNumber", "" + referenceNumber)
        intent.putExtra("referenceNumber", clientrefid)
        intent.putExtra("latitude", "" + lat)
        intent.putExtra("longitude", "" + lon)
        intent.putExtra("subMerchantId", ""+merchantCode)
        intent.putExtra("deviceManufacturerId", deviceManufacture);
        startActivityForResult(intent, _resultCodes)

    }*/


    private fun matmInitateMethode(
        partnerId: String,
        apiKey: String,
        merchantCode: String,
        userId: String,
        bcEmailId: String,
        referenceNumber: String,
        clientrefid: String,
        lon: String,
        lat: String) {

        val intent = Intent(requireContext(), HostActivity::class.java)
        intent.putExtra("partnerId", "" + partnerId)
        intent.putExtra("apiKey", "" + apiKey)
        intent.putExtra("merchantCode", "" + merchantCode)
        intent.putExtra("transactionType", ""+txn_type)
        if(txn_type.equals("BE")){
            intent.putExtra("amount", "0")
        }
        else{intent.putExtra("amount", ""+binding.etAmount.text.toString())
        }
        intent.putExtra("remarks", "Test Transaction")
        intent.putExtra("mobileNumber", "" + referenceNumber)
        intent.putExtra("referenceNumber", clientrefid)
        intent.putExtra("latitude", "" + lat)
        intent.putExtra("longitude", "" + lon)
        intent.putExtra("subMerchantId", ""+merchantCode)
        intent.putExtra("deviceManufacturerId", deviceManufacture);
        startActivityForResult(intent, _resultCode)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}