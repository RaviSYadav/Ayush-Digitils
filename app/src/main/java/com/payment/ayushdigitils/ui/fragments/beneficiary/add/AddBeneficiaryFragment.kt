package com.payment.ayushdigitils.ui.fragments.beneficiary.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPaySprintDmtBanksData
import com.payment.ayushdigitils.databinding.FragmentAddbeneficiaryBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.bank.BanksFragment
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class AddBeneficiaryFragment : BaseFragment(R.layout.fragment_addbeneficiary),BanksFragment.BankDialogListener {

    private var _binding:FragmentAddbeneficiaryBinding ? = null
    private val  binding get() = _binding!!

    private val args: AddBeneficiaryFragmentArgs by navArgs()
    private val viewModel by viewModel<AddBeneficiaryViewModel>()

    private lateinit var dialog: BanksFragment
    lateinit var listener: BanksFragment.BankDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentAddbeneficiaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {
        args.remitter.let { remitter ->
            binding.lblRemitterName.text = getString(
                R.string.remitter_full_name,
                remitter.fname,
                remitter.lname
            )

            binding.lblMaxAmount.text = getString(
                R.string.remitter_full_name,
                getString(R.string.Rs),
                remitter.bank1_limit.toString()

            )

            binding.lblRemitterNum.text = getString(
                R.string.remitter_full_name,
                "+91",
                remitter.mobile

            )

            binding.lblMinAmount.text = getString(
                R.string.remitter_full_name,
                getString(R.string.Rs),
                remitter.bank1_limit.toString()

            )
        }
        observer()


    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.etBank.setOnClickListener {
            dialog = BanksFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.btnSignup.setOnClickListener {
            if (isValidate()){
                addBeneficiaryData()
            }
        }
        binding.lblVerify.setOnClickListener {
            if (isValidate()){
             verifyBeneficiaryData()
            }
        }
    }

    private fun addBeneficiaryData(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", args.remitter.mobile)
            put("name", getString(R.string.remitter_full_name,args.remitter.fname,args.remitter.lname))
            put("benemobile", binding.etBeneficiaryMobile.text.toString())
            put("benename", binding.etAccHolderName.text.toString())
            put("benebank", binding.etBank.tag.toString())
            put("beneifsc", binding.etIfscCode.text.toString())
            put("beneaccount", binding.etAccountNumber.text.toString())
            put("type", "addbeneficiary")
        }
        viewModel.addBeneficiaryBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun verifyBeneficiaryData(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", args.remitter.mobile)
            put("name", getString(R.string.remitter_full_name,args.remitter.fname,args.remitter.lname))
            put("benemobile", binding.etBeneficiaryMobile.text.toString())
            put("benename", binding.etAccHolderName.text.toString())
            put("benebank", binding.etBank.tag.toString())
            put("beneifsc", binding.etIfscCode.text.toString())
            put("beneaccount", binding.etAccountNumber.text.toString())
            put("type", "accountverification")
        }
        viewModel.verifyBeneficiaryBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun isValidate():Boolean{
        if (binding.etBeneficiaryMobile.text.toString().isEmpty()){
            binding.etBeneficiaryMobile.error = "Please enter appropriate mobile number"
            binding.etBeneficiaryMobile.requestFocus()
            return false
        } else if (binding.etAccHolderName.text.toString().isEmpty()){
            binding.etAccHolderName.error = "Please enter appropriate Name"
            binding.etAccHolderName.requestFocus()
            return false
        }else if (binding.etIfscCode.text.toString().isEmpty()){
            binding.etIfscCode.error = "Please enter appropriate ifsc code"
            binding.etIfscCode.requestFocus()
            return false
        }else if (binding.etAccountNumber.text.toString().isEmpty()){
            binding.etAccountNumber.error = "Please enter appropriate account number"
            binding.etAccountNumber.requestFocus()
            return false
        }else if (binding.etBank.text.toString().isEmpty()){
            binding.etBank.error = "Please select appropriate bank"
            binding.etBank.requestFocus()
            return false
        }
        return true
    }
    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.btnSignup.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })

        viewModel.getDmtRegistrationResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("beneficiary registration Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {

                            findNavController().navigateUp()

                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "Add beneficiary ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
        viewModel.getBeneficiaryVerifyResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("beneficiary Verify Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {
                            binding.etAccHolderName.setText(data.benename)
                            viewModel.toastMsg.value = data.message
                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "beneficiary ex:")
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun chooseBankClick(bank: NetworkPaySprintDmtBanksData) {
        binding.etBank.setText(bank.name)
        binding.etBank.setTag(bank.bankid)
        binding.etIfscCode.setText(bank.ifsc)
        dialog.dismiss()
    }


}