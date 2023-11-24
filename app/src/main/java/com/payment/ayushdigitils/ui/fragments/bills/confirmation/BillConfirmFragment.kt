package com.payment.ayushdigitils.ui.fragments.bills.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkBillParamsData
import com.payment.ayushdigitils.databinding.FragmentBillconfirmBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseDialogFragment
import com.payment.ayushdigitils.ui.fragments.bills.ShareBillViewModel
import com.payment.ayushdigitils.ui.utils.Constanse
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Date


class BillConfirmFragment : BaseDialogFragment(R.layout.fragment_billconfirm) {
    private var _binding :FragmentBillconfirmBinding? = null
    private val binding get() = _binding!!
    private val args : BillConfirmFragmentArgs by navArgs()

    private val viewModel by viewModel<BillPaymentViewModel>()
    private val shareViewModel by sharedViewModel<ShareBillViewModel>()

    private val paramsList: MutableList<NetworkBillParamsData> = ArrayList<NetworkBillParamsData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBillconfirmBinding.inflate(inflater,container,false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()


    }


    override fun initView(){
        args.billersData.let {data->
            binding.lblAmount.text = getString(
                R.string.remitter_full_name,
                resources.getString(R.string.Rs),
                data.bill_amount
            )

            Constanse.setStaticProviderImg(data.provider_name,binding.ivArt,requireContext(),data.provider_type)
            binding.lblConsumerName.text = data.consumer_name
            binding.lblProviderName.text = data.provider_name


        }

    }

    override fun initClick(){
        binding.imgClose.setOnClickListener {
            dialog?.dismiss()
        }
        binding.btnSubmit.setOnClickListener {
            if (isValid()){
                validateBill()
            }
        }
    }


    private fun validateBill(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", args.billersData.type)
            put("provider_id", args.billersData.provider_id)
            put("biller", args.billersData.consumer_name)
            put("duedate", args.billersData.due_date)
            put("amount", args.billersData.bill_amount)
            put("bu", args.billersData.bu)
            put("mode", args.billersData.bbps_mode)
            put("TransactionId", args.billersData.TransactionId)
            put("dateTime", Constanse.SHOWING_DATE_FORMAT.format(Date()))
            for (i in paramsList.indices) {
                val key = "number$i"
                put(key, paramsList[i].fieldInputValue)
            }
        }
        viewModel.billPaymentRequest.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {

        shareViewModel.getDataList().observe(viewLifecycleOwner) { dataList ->
            paramsList.addAll(dataList)
        }

        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.btnSubmit.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getBillPaymentResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("bill params Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        viewModel.toastMsg.value = data.message


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "bill confiramation ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    private fun isValid(): Boolean {
        var flag = true
        for (model in paramsList) {
            val inputValue: String? = model.fieldInputValue
            if (inputValue.isNullOrEmpty()) {
                viewModel.toastMsg.value  = model.paramname.toLowerCase() + " " + "is required"
                flag = false
                break
            }
        }
        return flag
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}