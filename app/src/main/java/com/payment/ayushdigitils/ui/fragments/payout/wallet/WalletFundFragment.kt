package com.payment.ayushdigitils.ui.fragments.payout.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.payment.ayushdigitils.NavGraphInvoiceDirections
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.FundBankList
import com.payment.ayushdigitils.data.remote.FundPayModes
import com.payment.ayushdigitils.databinding.DialogItemUserBankBinding
import com.payment.ayushdigitils.databinding.FragmentWalletFundBinding
import com.payment.ayushdigitils.databinding.ViewHolderItemBankAccountsBinding
import com.payment.ayushdigitils.databinding.ViewHolderItemPaymentModeBinding
import com.payment.ayushdigitils.ex.transformIntoDatePicker
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceModel
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceViewModel
import com.payment.ayushdigitils.ui.utils.Constanse
import com.payment.ayushdigitils.ui.utils.GenericAdapter
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.payment.ayushdigitils.ex.manageClick
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class WalletFundFragment : BaseFragment(R.layout.fragment_wallet_fund) {

    private  var _binding: FragmentWalletFundBinding? = null
    private val binding get() = _binding!!

    private val invoiceList: MutableList<InvoiceModel> = mutableListOf()

    private val viewModel by viewModel<WalletFundViewModel>()
    private val invoiceViewModel by sharedViewModel<InvoiceViewModel>()

    private lateinit var fundBankDialog: BottomSheetDialog
    private val fundBankDialogBinding: DialogItemUserBankBinding by lazy {
        DialogItemUserBankBinding.inflate(
            LayoutInflater.from(
                requireContext()
            )
        )
    }
    private lateinit var fundAdapter: GenericAdapter<FundBankList, ViewHolderItemBankAccountsBinding>

    private lateinit var paymentModeDialog: BottomSheetDialog
    private val fundPaymentModeBinding: DialogItemUserBankBinding by lazy {
        DialogItemUserBankBinding.inflate(
            LayoutInflater.from(
                requireContext()
            )
        )
    }
    private lateinit var paymentModeAdapter: GenericAdapter<FundPayModes, ViewHolderItemPaymentModeBinding>

    private val minDate: Long by lazy {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -100)
        cal.timeInMillis
    }
    private val maxDate: Long by lazy {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -13)
        cal.timeInMillis
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletFundBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()
    }
    override fun initView() {
        getFundBank()
        showCalender()
        try {
            showBankDialog()
            showPaymentModeDialog()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun initClick() {
        binding.etBank.setOnClickListener {
            fundBankDialog.show()
        }
        binding.etPaymentMode.setOnClickListener {
            paymentModeDialog.show()
        }
        binding.btnProceed.setOnClickListener {
            if (isValidate()){
                requestFundRequest()
            }
        }
    }

    private fun getFundBank(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", "getfundbank")
        }
        viewModel.requestFundBank.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }


    private fun showBankDialog(){
        fundBankDialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme).apply {
            setContentView(fundBankDialogBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)

        }

        fundAdapter = GenericAdapter(
            layoutInflater = ViewHolderItemBankAccountsBinding::inflate, // Replace with your actual view binding class
            bind = { binding, item ->
                binding.lblBankName.text = item.name
                binding.lblAccountNumber.text = item.account
                binding.lblIfscCode.text = item.ifsc
            },
            onItemClick = { item ->
                binding.etBank.setText(item.name)
                binding.etBank.tag = item.id
                fundBankDialog.dismiss()

            }
        )
        fundBankDialogBinding.recyclerTrack.layoutManager = LinearLayoutManager(requireContext())
        fundBankDialogBinding.recyclerTrack.adapter = fundAdapter

    }
    private fun showPaymentModeDialog(){
        paymentModeDialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme).apply {
            setContentView(fundPaymentModeBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)

        }

        paymentModeAdapter = GenericAdapter(
            layoutInflater = ViewHolderItemPaymentModeBinding::inflate, // Replace with your actual view binding class
            bind = { binding, item ->
                binding.lblPaymentMode.text = item.name
                binding.lblPaymentMode.tag = item.id
            },
            onItemClick = { item ->
                binding.etPaymentMode.setText(item.name)
                binding.etPaymentMode.tag = item.id
                paymentModeDialog.dismiss()


            }
        )
        fundPaymentModeBinding.recyclerTrack.layoutManager = LinearLayoutManager(requireContext())
        fundPaymentModeBinding.recyclerTrack.adapter = paymentModeAdapter

    }

    private fun requestFundRequest(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", "request")
            put("fundbank_id", binding.etBank.tag.toString())
            put("paymode", binding.etPaymentMode.tag.toString())
            put("paydate", binding.etDate.text.toString())
            put("amount", binding.etRequestAmount.text.toString())
            if (binding.etReferenceNumber.text.toString().isNotEmpty()) {
                put("ref_no", binding.etReferenceNumber.text.toString())

        }
            put("pin",binding.etTransactionPin.text.toString())

        }
        viewModel.requestFundRequest.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.btnProceed.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getFundBankResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.status.equals("ERR")){
                            viewModel.toastMsg.value = data.message
                        }else {
                            if (data.statuscode == "TXN") {

                                data.data?.let {
                                    it.banks?.let { bankList ->
                                        fundAdapter.setData(bankList)
                                    }

                                    it.paymodes?.let { payModes ->
                                        paymentModeAdapter.setData(payModes)
                                    }

                                }
                            } else {
//                            viewModel.toastMsg.value = data.message
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "Wallet fund ex:")
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
                        invoiceList.add(InvoiceModel("Ref Number", binding.etReferenceNumber.text.toString()))
                        invoiceList.add(InvoiceModel("Date", Constanse.COMMON_DATE_FORMAT.format(Date())))
                        invoiceList.add(InvoiceModel("Trans Type", "Load Wallet Request"))
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
                    Timber.e(it.exception, "login ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    private fun showCalender(){
        val updatedDob = binding.etDate.text.toString()
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedCalendar = try {
            cal.time = sdf.parse(updatedDob)!!
            cal
        } catch (e: Exception) {
            Timber.d("Unable to parse date of birth")
            cal
        }
        binding.etDate.transformIntoDatePicker(
            requireContext(),
            "yyyy-MM-dd",
            minDate,
            maxDate,
            parsedCalendar
        )
    }
    private fun isValidate():Boolean{
        if (binding.etBank.text.toString().isEmpty()){
            binding.etBank.error = "Please select appropriate bank"
            binding.etBank.requestFocus()
            return false
        } else if (binding.etReferenceNumber.text.toString().isEmpty()){
            binding.etReferenceNumber.error = "Please enter appropriate reference number"
            binding.etReferenceNumber.requestFocus()
            return false
        }else if (binding.etDate.text.toString().isEmpty()){
            binding.etDate.error = "Please enter appropriate date"
            binding.etDate.requestFocus()
            return false
        }else if (binding.etPaymentMode.text.toString().isEmpty()){
            binding.etPaymentMode.error = "Please select appropriate payment mode"
            binding.etPaymentMode.requestFocus()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

}