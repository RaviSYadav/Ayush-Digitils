package com.payment.ayushdigitils.ui.fragments.operator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPrepaidProvidersData
import com.payment.ayushdigitils.databinding.FragmentBilloperatorBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.prepaid.PrepaidViewModel
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class BillOperatorFragment : BaseFragment(R.layout.fragment_billoperator),BillProviderAdapter.AdapterCallbacks {
    private var _binding: FragmentBilloperatorBinding? = null
    // This property is only valid between onCreateView and

    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModel<PrepaidViewModel>()


    private val args: BillOperatorFragmentArgs by navArgs()

    private lateinit var providerAdapter: BillProviderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentBilloperatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initClick()
        observer()
    }

    override fun initView() {
        prepareBeneficiaryRecyclerView()
        getProviderData()
    }
    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchViewProvider.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                providerAdapter.filter.filter(newText)
                return true
            }
        })
    }
    private fun prepareBeneficiaryRecyclerView() {
        providerAdapter = BillProviderAdapter(this)
        binding.rvProvider.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = providerAdapter
        }

    }
    private fun getProviderData(){
        val jsonObject = JSONObject().apply {
            put("type", args.uniqueSlug)
        }
        viewModel.providerJson.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getPrepaidProviders.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("provider Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {
                            if (data.data.isEmpty()){
                                binding.errorContainer.visible()
                                binding.scrollView.gone()
                            }else{
                                data.data.let {res->

                                    binding.errorContainer.gone()
                                    binding.scrollView.visible()
                                    binding.lblTitle.text = args.title
                                    providerAdapter.setProviders(res,args.uniqueSlug)

                                }
                            }


                        } else {
                            viewModel.toastMsg.value = data.message
                        }
                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "provider ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onProviderClicked(
        networkPrepaidProvidersDa: NetworkPrepaidProvidersData,
        position: Int
    ) {
        findNavController().navigate(BillOperatorFragmentDirections.actionBillOperatorToFetchBbpsBillFragment(networkPrepaidProvidersDa.id.toString()))
    }
}