package com.payment.ayushdigitils.ui.fragments.beneficiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPaySprintBeneficiaryData
import com.payment.ayushdigitils.databinding.FragmentBeneficiaryBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.vo.Resource
import com.shreyaspatil.MaterialDialog.MaterialDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class BeneficiaryFragment : BaseFragment(R.layout.fragment_beneficiary),BeneficiaryAdapter.OnBeneficiaryClickListener {

    private var _binding: FragmentBeneficiaryBinding? = null
    private val binding get() = _binding!!

    private val args: BeneficiaryFragmentArgs by navArgs()
    private val viewModel by viewModel<BeneficiaryViewModel>()

    private lateinit var beneficiaryAdapter: BeneficiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeneficiaryBinding.inflate(inflater, container, false)
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

        prepareBeneficiaryRecyclerView()

    }

    private fun prepareBeneficiaryRecyclerView() {
        beneficiaryAdapter = BeneficiaryAdapter(this)
        binding.recBeneficiary.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = beneficiaryAdapter
        }

    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.fabAddBeneficiary.setOnClickListener {
           findNavController().navigate(BeneficiaryFragmentDirections.actionBeneficiaryFragmentToAddBeneficiaryFragment(args.remitter))
        }
        binding.swipeRefresh.setOnRefreshListener {
            getBeneficiaryData()
        }
    }

    private fun getBeneficiaryData(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", args.remitter.mobile)
            put("type", "verification")
        }
        viewModel.beneficiaryBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }
    private fun deleteBeneficiaryData(beneficiary: NetworkPaySprintBeneficiaryData){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("mobile", args.remitter.mobile)
            put("rid", args.remitter.mobile)
            put("rname", getString(R.string.remitter_full_name,args.remitter.fname,args.remitter.lname))
            put("bid", beneficiary.bene_id)
            put("type", "benedelete")
        }
        viewModel.deleteBeneficiaryBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getBeneficiaryBodyResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {

                            if (data.benedata.isNotEmpty()){
                                beneficiaryAdapter.setBeneficiaryItems(data.benedata)
                            }else{
                                beneficiaryAdapter.setBeneficiaryItems(listOf())
                            }



                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "login ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
        viewModel.deleteBeneficiaryResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("delete beneficiary Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {

                          getBeneficiaryData()

                        } else {
                            viewModel.toastMsg.value = data.message
                        }


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



    override fun onResume() {
        super.onResume()
        getBeneficiaryData()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTransferClicked(position: Int, beneficiary: NetworkPaySprintBeneficiaryData) {
        findNavController().navigate(BeneficiaryFragmentDirections.actionBeneficiaryFragmentToMoneyTransactionFragment(args.remitter,beneficiary))
    }

    override fun onDeleteClicked(position: Int, beneficiary: NetworkPaySprintBeneficiaryData) {
        deleteConfirmation(beneficiary)
    }
    private fun deleteConfirmation(beneficiary: NetworkPaySprintBeneficiaryData){
        MaterialDialog.Builder(requireActivity())
            .apply {
                setTitle("Delete?")
                setMessage("Are you sure want to delete this file?")
                setCancelable(false)
                setAnimation("delete_anim.json")
                setPositiveButton("Delete",R.drawable.ic_delete){ dialogInterface, which ->
                    deleteBeneficiaryData(beneficiary)
                    dialogInterface.dismiss()
                }
                setNegativeButton("Cancel", android.R.drawable.ic_delete){ dialogInterface, which ->
                    // Delete Operation
                    dialogInterface.dismiss()
                }
                build().show()
            }

    }
}