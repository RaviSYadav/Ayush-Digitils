package com.payment.ayushdigitils.ui.fragments.transection

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.DialogMoneyTransactionBinding
import com.payment.ayushdigitils.databinding.FragmentMoneyTransectionBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MoneyTransactionFragment : BaseFragment(R.layout.fragment_money_transection) {
    private var _binding: FragmentMoneyTransectionBinding? = null
    private val binding get() = _binding!!
    private val args:MoneyTransactionFragmentArgs by navArgs()
    private val viewModel by viewModel<MoneyTransactionViewModel>()



    companion object{
        private val payment_mode = arrayOf("IMPS", "NEFT")
        private val payment_via = arrayOf("XDMT", "DMT")
    }

    private lateinit var dialog: Dialog
    private val dialogBinding: DialogMoneyTransactionBinding by lazy {
        DialogMoneyTransactionBinding.inflate(
            LayoutInflater.from(
                requireContext()
            )
        )
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMoneyTransectionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {
        val adapterGender: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, payment_mode)
        binding.spinTransactionMode.setAdapter(adapterGender)
        val adapterVia: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, payment_via)
        binding.spinTransactionVia.setAdapter(adapterVia)
        binding.spinTransactionVia.setText(binding.spinTransactionVia.adapter.getItem(0).toString(), false);
        observer()

        args.remitter.let {remitter->
            binding.lblRemitterName.text = "Sender Name : ${getString(R.string.remitter_full_name,remitter.fname,remitter.lname)}"
            binding.lblRemitterNum.text = "Sender Number : ${getString(R.string.remitter_full_name," + 91",remitter.mobile)}"
        }
        args.beneficiary.let {benificiary->
            binding.lblBeneficiaryName.text = "Beneficiary Name : ${benificiary.name}"
            binding.lblBeneficiaryAccount.text = "Account : ${benificiary.accno}"
            binding.lblBeneficiaryIfsc.text = "IFSC Code : ${benificiary.ifsc}"
            binding.lblBeneficiaryIfsc.text = "Bank Name : ${benificiary.bankname}"

           try {
               dialogBinding.lblAccount.text = benificiary.accno
               dialogBinding.lblBeneficiaryName.text = benificiary.name
               dialogBinding.lblBeneficiaryBank.text = benificiary.bankname
           }catch (e:Exception){
               e.printStackTrace()
           }

        }

        try {
            showConfirmationDialog()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.btnProceed.setOnClickListener {
            if (isValidate()){
                dialogBinding.lblTransactionAmount.text = getString(R.string.remitter_full_name,getString(R.string.Rs),binding.etTransactionAmount.text.toString())

                dialog.show()


            }
        }
        dialogBinding.btnJoin.setOnClickListener {
            requestMoneyTransaction()
            dialog.cancel()

        }
        dialogBinding.btnCancel.setOnClickListener {

            dialog.cancel()
        }
    }

    private fun requestMoneyTransaction(){

        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", "transfer")
            put("txntype", "")
            put("mobile", args.remitter.mobile)
            put("name", args.beneficiary.name)
            put("benename", args.beneficiary.name)
            put("beneid", args.beneficiary.bene_id)
            put("mode", binding.spinTransactionMode.text.toString().lowercase())
            put("amount", binding.etTransactionAmount.text.toString())
            put("benebank", args.beneficiary.bankid)
            put("benebankName", args.beneficiary.bankname)
            put("beneifsc", args.beneficiary.ifsc)
            put("benemobile", args.remitter.mobile)
            put("beneaccount", args.beneficiary.accno)
            put("pin", binding.etTransactionPin.text.toString())
        }
        viewModel.transactionBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            dialogBinding.btnJoin.manageClick(it)
            binding.btnProceed.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getMoneyTransactionResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("transaction Res---- : ${it.data}")
                    it.data?.let { data ->
//                        NetworkPaySprintMoneyTransactionData

                        viewModel.showLoader.value = false
                        if (data.statuscode?.isNotEmpty() == true && data.statuscode != "ERR") {

                            findNavController().navigate(MoneyTransactionFragmentDirections.actionMoneyTransactionFragmentToPaySprintDmtInvoiceFragment(args.remitter,args.beneficiary,data,binding.etTransactionAmount.text.toString()))

                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "transaction ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }


    private fun isValidate():Boolean{
        if (binding.etTransactionAmount.text.toString().isEmpty()){
            binding.etTransactionAmount.error = "Please enter appropriate account number"
            binding.etTransactionAmount.requestFocus()
            return false
        }else if (binding.etTransactionPin.text.toString().isEmpty()){
            binding.etTransactionPin.error = "Please enter appropriate T-Pin"
            binding.etTransactionPin.requestFocus()
            return false
        }else if (binding.spinTransactionMode.text.toString().isEmpty()){
            binding.spinTransactionMode.error = "Please select appropriate bank"
            binding.spinTransactionMode.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showConfirmationDialog(){
        dialog = Dialog(requireContext()).apply {
            setContentView(dialogBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)

        }

    }

}