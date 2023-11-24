package com.payment.ayushdigitils.ui.fragments.dth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPrepaidProvidersData
import com.payment.ayushdigitils.databinding.DialogRechargeTransactionBinding
import com.payment.ayushdigitils.databinding.FragmentDthBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.dmt.invoice.PaySprintDmtInvoiceFragment
import com.payment.ayushdigitils.ui.fragments.prepaid.PrepaidViewModel
import com.payment.ayushdigitils.ui.fragments.prepaid.provider.PrepaidOperatorDialogFragment
import com.payment.ayushdigitils.ui.utils.Constanse
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.fragments.browseplan.RechargePlansFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Date


class DTHFragment : BaseFragment(R.layout.fragment_dth),
    PrepaidOperatorDialogFragment.OperatorDialogListener
    , RechargePlansFragment.PlansDialogListener{

    private var _binding:FragmentDthBinding? = null
    private val binding get() = _binding!!


    private lateinit var providerDialog: PrepaidOperatorDialogFragment
    lateinit var listener: PrepaidOperatorDialogFragment.OperatorDialogListener

    private lateinit var rechargeDialog: BottomSheetDialog
    private val rechargeDialogBinding: DialogRechargeTransactionBinding by lazy {
        DialogRechargeTransactionBinding.inflate(
            LayoutInflater.from(
                requireContext()
            )
        )
    }

    private val viewModel by viewModel<PrepaidViewModel>()

    private lateinit var dialog: RechargePlansFragment
    private lateinit var plansDialogListener: RechargePlansFragment.PlansDialogListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
        plansDialogListener = this
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            showConfirmationDialog()
        }catch (e:Exception){
            e.printStackTrace()
        }

        initView()
        initClick()

    }

    override fun initView() {

        observer()
    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etOperator.setOnClickListener {
            if (isProviderValidate()){
                providerDialog = PrepaidOperatorDialogFragment(listener,binding.etMobileNumber.text.toString(),"dth")
                providerDialog.show(this.parentFragmentManager, "tag")
            }
        }
        binding.lblViewPlans.setOnClickListener {
            if (isValidateForPlans()) {
                dialog = RechargePlansFragment(
                    type = "dth",
                    mobile = binding.etMobileNumber.text.toString(),
                    provider_id = binding.etOperator.tag.toString(),
                    provider_name = binding.etOperator.text.toString(),
                    listener = plansDialogListener
                )
                dialog.show(this.parentFragmentManager, "tag")
            }
        }
        binding.btnRecharge.setOnClickListener {
            if (isValidate()){
                rechargeDialogBinding.lblMobileNumber.text = getString(
                    R.string.remitter_full_name,
                    "+91",
                    binding.etMobileNumber.text.toString()
                )
                rechargeDialogBinding.lblRechargeAmount.text = getString(
                    R.string.remitter_full_name,
                    resources.getString(R.string.Rs),
                    binding.etRechargeAmount.text.toString()
                )

                rechargeDialog.show()

            }
//            findNavController().navigate(PrepaidRechargeFragmentDirections.actionPrepaidRechargeToSuccessFragment())
        }


        rechargeDialogBinding.btnJoin.setOnClickListener {
            requestRecharge()
            rechargeDialog.cancel()



        }
        rechargeDialogBinding.btnCancel.setOnClickListener {

            rechargeDialog.cancel()
        }
    }

    private fun isProviderValidate():Boolean{
        if (binding.etMobileNumber.text.toString().trim().isEmpty()|| binding.etMobileNumber.text.toString().length<=9){
            binding.etMobileNumber.error = "Please enter appropriate mobile number"
            binding.etMobileNumber.requestFocus()
            return false
        }
        return true
    }
    private fun isValidate():Boolean{
        if (binding.etMobileNumber.text.toString().isEmpty()){
            binding.etMobileNumber.error = "Please enter appropriate mobile number"
            binding.etMobileNumber.requestFocus()
            return false
        }else if (binding.etOperator.text.toString().isEmpty()){
            binding.etOperator.error = "Please select appropriate operator"
            binding.etOperator.requestFocus()
            return false
        }else if (binding.etRechargeAmount.text.toString().isEmpty()){
            binding.etOperator.error = "Please enter appropriate amount"
            binding.etOperator.requestFocus()
            return false
        }
        return true
    }
    private fun isValidateForPlans():Boolean{
        if (binding.etMobileNumber.text.toString().isEmpty()){
            binding.etMobileNumber.error = "Please enter appropriate mobile number"
            binding.etMobileNumber.requestFocus()
            return false
        }else if (binding.etOperator.text.toString().isEmpty()){
            binding.etOperator.error = "Please select appropriate operator"
            binding.etOperator.requestFocus()
            return false
        }
        return true
    }


    override fun operatorClick(networkPrepaidProvidersDa: NetworkPrepaidProvidersData) {
        binding.etOperator.setText(networkPrepaidProvidersDa.name)
        binding.etOperator.setTag(networkPrepaidProvidersDa.id)
        rechargeDialogBinding.lblProvider.text = networkPrepaidProvidersDa.name
        rechargeDialogBinding.lblProvider.tag = networkPrepaidProvidersDa.id
        Constanse.setStaticProviderImg( networkPrepaidProvidersDa.name,rechargeDialogBinding.ivArt, requireContext(), "mobile")
        providerDialog.dismissNow()

    }


    private fun showConfirmationDialog(){
        rechargeDialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme).apply {
            setContentView(rechargeDialogBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)

        }

        rechargeDialogBinding.lblWarning.isSelected = true

    }

    private fun requestRecharge(){
        val jsonObject = JSONObject().apply {
            put("user_id",prefs.getUserId())
            put("apptoken",prefs.getAppToken())
            put("number", binding.etMobileNumber.text.toString())
            put("provider_id", binding.etOperator.tag.toString())
            put("amount", binding.etRechargeAmount.text.toString())
            put("pin", rechargeDialogBinding.etTransactionPin.text.toString())
        }
        viewModel.rechargePayJson.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            rechargeDialogBinding.btnJoin.manageClick(it)
            binding.btnRecharge.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getRechargePayResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("dth Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {
                            val date = PaySprintDmtInvoiceFragment.COMMON_DATE_FORMAT.format(Date())
                            val action = DTHFragmentDirections.actionDTHFragmentToSuccessFragment(data,binding.etMobileNumber.text.toString(),binding.etRechargeAmount.text.toString(),"Dth Recharge",date)
                            findNavController().navigate(action)
                        } else {
                            viewModel.toastMsg.value = data.message
                        }
                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "dth ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun choosePlansClick(amount: String) {
        binding.etRechargeAmount.setText(amount)
        dialog.dismiss()
    }
}