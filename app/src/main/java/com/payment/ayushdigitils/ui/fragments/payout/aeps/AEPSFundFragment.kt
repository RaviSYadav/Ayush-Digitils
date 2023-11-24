package com.payment.ayushdigitils.ui.fragments.payout.aeps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.payment.ayushdigitils.NavGraphInvoiceDirections
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.FundUserBankData
import com.payment.ayushdigitils.databinding.DialogItemUserBankBinding
import com.payment.ayushdigitils.databinding.FragmentAepsFundBinding
import com.payment.ayushdigitils.databinding.ViewHolderItemBankAccountsBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceModel
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceViewModel
import com.payment.ayushdigitils.ui.utils.Constanse.COMMON_DATE_FORMAT
import com.payment.ayushdigitils.ui.utils.GenericAdapter
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.payment.ayushdigitils.ex.manageClick
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Date
import java.util.Locale


class AEPSFundFragment : BaseFragment(R.layout.fragment_aeps_fund) {

    private var _binding:FragmentAepsFundBinding? = null
    private val binding get() = _binding!!

    private val invoiceList: MutableList<InvoiceModel> = mutableListOf()

    private val viewModel by viewModel<AEPSFundViewModel>()
    private val invoiceViewModel by sharedViewModel<InvoiceViewModel>()


    private val fundType = arrayOf("To Wallet", "To Bank")
    private val modeType = arrayOf("NEFT", "IMPS", "MANUAL")

    private lateinit var userBankDialog: BottomSheetDialog
    private val userBankDialogBinding: DialogItemUserBankBinding by lazy {
        DialogItemUserBankBinding.inflate(
            LayoutInflater.from(
                requireContext()
            )
        )
    }
    private lateinit var adapter: GenericAdapter<FundUserBankData,ViewHolderItemBankAccountsBinding> // Replace with your actual data model and view binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAepsFundBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()

    }


    override fun initView() {
        try {
            showConfirmationDialog()
        }catch (e:Exception){
            e.printStackTrace()
        }

        getUserFundBank()
        val adapterModeType: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, modeType)
        binding.actPaymentMode.setAdapter(adapterModeType)

        val adapterFundType: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, fundType)
        binding.actRequestType.setAdapter(adapterFundType)
    }


    override fun initClick() {
        binding.etBank.setOnClickListener {
            try {
                    userBankDialog.show()

            }catch (e:Exception){
                viewModel.toastMsg.value = "Bank list is not avalable"
            }

        }
        binding.btnProceed.setOnClickListener {
            if (isValidate()){
                requestFundRequest()
            }
        }
    }

    private fun getUserFundBank(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", prefs.getLoginID())
        }
        viewModel.requestUserBank.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun requestFundRequest(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", getFundType())
            put("amount", binding.etRequestAmount.text.toString())
            put("pin", binding.etTransactionPin.text.toString())
            put("account", binding.etAccountNumber.text.toString())
            put("bank", binding.etBank.text.toString())
            put("ifsc", binding.etIfscCode.text.toString())
            put("mode", binding.actPaymentMode.text.toString())
        }
        viewModel.requestFundRequest.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun isValidate():Boolean{
        if (binding.etBank.text.toString().isEmpty()){
            binding.etBank.error = "Please select appropriate bank"
            binding.etBank.requestFocus()
            return false
        } else if (binding.etAccountNumber.text.toString().isEmpty()){
            binding.etAccountNumber.error = "Please enter appropriate account number"
            binding.etAccountNumber.requestFocus()
            return false
        }else if (binding.etIfscCode.text.toString().isEmpty()){
            binding.etIfscCode.error = "Please enter appropriate ifsc code"
            binding.etIfscCode.requestFocus()
            return false
        }else if (binding.actPaymentMode.text.toString().isEmpty()){
            binding.actPaymentMode.error = "Please select appropriate payment mode"
            binding.actPaymentMode.requestFocus()
            return false
        }else if (binding.actRequestType.text.toString().isEmpty()){
            binding.actRequestType.error = "Please select appropriate request type"
            binding.actRequestType.requestFocus()
            return false
        }else if (binding.etRequestAmount.text.toString().isEmpty()){
            binding.etRequestAmount.error = "Please enter appropriate amount"
            binding.etRequestAmount.requestFocus()
            return false
        }else if (binding.etTransactionPin.text.toString().isEmpty()){
            binding.etTransactionPin.error = "Please enter appropriate t-pin"
            binding.etTransactionPin.requestFocus()
            return false
        }
        return true
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.btnProceed.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getUserBankResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {

                            if (data.data.isNotEmpty()){
                                adapter.setData(data.data)
                            }else{
                                binding.etBank.tag= "Bank list is not avalable"
                            }


                        } else {
//                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "login ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
        viewModel.getFundRequestResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        invoiceViewModel.clearInvoiceList()
                        invoiceList.add(InvoiceModel("Txn Id", data.txnid))
                        invoiceList.add(InvoiceModel("Bank", binding.etBank.text.toString()))
                        invoiceList.add(InvoiceModel("Date", COMMON_DATE_FORMAT.format(Date())))
                        invoiceList.add(InvoiceModel("Trans Type", "Aeps fund Request"))
                        invoiceList.add(InvoiceModel("Amount", getString(R.string.remitter_full_name,getString(R.string.Rs),binding.etRequestAmount.text.toString())))
                        invoiceList.add(InvoiceModel("Status","success"))

                        invoiceViewModel.setInvoiceList(invoiceList)

                        findNavController().navigate(
                            NavGraphInvoiceDirections.actionGlobalInvoiceFragment(data.txnid?:"",data.message?:"Something went wrong, try again")
                        )





                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "Aeps fund ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }


    private fun showConfirmationDialog(){
        userBankDialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme).apply {
            setContentView(userBankDialogBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)

        }

        adapter = GenericAdapter(
            layoutInflater = ViewHolderItemBankAccountsBinding::inflate, // Replace with your actual view binding class
            bind = { binding, item ->
                binding.lblBankName.text = item.bankname
                binding.lblAccountNumber.text = item.account
                binding.lblIfscCode.text = item.ifsc
            },
            onItemClick = { item ->
                binding.etBank.setText(item.bankname)
                binding.etAccountNumber.setText(item.account)
                binding.etIfscCode.setText(item.ifsc)

            }
        )
        userBankDialogBinding.recyclerTrack.layoutManager = LinearLayoutManager(requireContext())
        userBankDialogBinding.recyclerTrack.adapter = adapter

    }


    private fun getFundType(): String {
        if (binding.actRequestType.text.toString().lowercase(Locale.getDefault())
                .equals("To Wallet", ignoreCase = true)
        ) {
            return "wallet"
        }
        if (binding.actRequestType.text.toString().lowercase(Locale.getDefault())
                .equals("To Bank", ignoreCase = true)
        ) {
            return "bank"
        }
         else {
            return   ""
        }
    }

}