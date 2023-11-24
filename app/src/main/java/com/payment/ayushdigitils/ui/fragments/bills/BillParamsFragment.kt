package com.payment.ayushdigitils.ui.fragments.bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkBillParamsData
import com.payment.ayushdigitils.databinding.FragmentBillParamsBinding
import com.payment.ayushdigitils.ex.manageClick
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.utils.Constanse
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Date


class BillParamsFragment : BaseFragment(R.layout.fragment_bill_params),BillParamsAdapter.OnParamsClickListener {

    private val paramsList: MutableList<NetworkBillParamsData> = ArrayList<NetworkBillParamsData>()

    private  var _binding: FragmentBillParamsBinding?= null
    private val binding get() = _binding!!

    private lateinit var billParamsAdapter: BillParamsAdapter

    private val args: BillParamsFragmentArgs by navArgs()

    private val viewModel by viewModel<BillParamsViewModel>()
    private val shareViewModel by sharedViewModel<ShareBillViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBillParamsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dataList.addAll(FetchBills.getBillPayParam(args.type!!))

        initView()
        initClick()

    }

    override fun initView() {
        prepareCategoriesRecyclerView()
        getBillParams()
        observer()
    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnRecharge.setOnClickListener {
            if (isValid()){
                validateBill()

            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun prepareCategoriesRecyclerView() {
        billParamsAdapter = BillParamsAdapter(this)
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = billParamsAdapter
        }


    }

    private fun getBillParams(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("provider_id", args.providerId)
        }
        viewModel.providerId.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun validateBill(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", "validate")
            put("provider_id", args.providerId)
            put("duedate", Constanse.SHOWING_DATE_FORMAT.format( Date()))
            for (i in paramsList.indices) {
                val key = "number$i"
                put(key, paramsList[i].fieldInputValue)
            }
        }
        viewModel.validateRequest.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.btnRecharge.manageClick(it)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getBillParamsResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("bill params Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        binding.lblTitle.text = data.name
                        binding.lblTitle.tag = data.type
                        Constanse.setStaticProviderImg( data.name,binding.imgArt, requireContext(), data.type)
                        Constanse.setStaticProviderImg( data.name,binding.ivArt, requireContext(), data.type)
                        paramsList.addAll(data.parame)

                        shareViewModel.setDataList(paramsList)
                        billParamsAdapter.setParams(data.parame)
                        binding.btnRecharge.visible()

                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "bill params ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
        viewModel.getBillValidateResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("bill validate Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        data.message?.let {message->
                            viewModel.toastMsg.value = message
                        }

                        if (!data.statuscode.isNullOrEmpty()) {
                            shareViewModel.setDataList(paramsList)
                            val action =
                                BillParamsFragmentDirections.actionFetchBbpsBillFragmentToShowBillfetchedFragment(
                                    data.data,
                                    args.providerId,
                                    binding.lblTitle.text.toString(),
                                    binding.lblTitle.tag.toString(),

                                )
                            findNavController().navigate(action)
                        }

                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "bill validate ex:")
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

    override fun onGetBillParamsValue(position: Int, params: NetworkBillParamsData) {
        paramsList[position] = params
    }
}