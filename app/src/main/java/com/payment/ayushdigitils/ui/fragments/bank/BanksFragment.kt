package com.payment.ayushdigitils.ui.fragments.bank

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPaySprintDmtBanksData
import com.payment.ayushdigitils.databinding.FragmentBanksBinding
import com.payment.ayushdigitils.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class BanksFragment(listener: BankDialogListener) : BottomSheetDialogFragment(R.layout.fragment_banks),BanksAdapter.OnBankClickListener {

    private var _binding: FragmentBanksBinding?= null
    private val binding get() = _binding!!
    private var mBottomSheetListener: BankDialogListener?=null
    private val viewModel by viewModel<BanksViewModel>()

    private lateinit var banksAdapter: BanksAdapter

    init {
        this.mBottomSheetListener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBanksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        prepareBeneficiaryRecyclerView()

        initClick()
    }

    private fun initClick(){
        binding.searchViewBanks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                banksAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onBankClicked(position: Int, bank: NetworkPaySprintDmtBanksData) {
        mBottomSheetListener?.chooseBankClick(bank)
    }

    private fun prepareBeneficiaryRecyclerView() {
        banksAdapter = BanksAdapter(this)
        binding.rvBank.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = banksAdapter
        }

    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            binding.progressBar.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
//            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getBankResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("bank Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {
                            data.data.let {res->
                                banksAdapter.setBanks(res.banks)
                            }
                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "bank ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

        /** attach listener from parent fragment */
        try {
            mBottomSheetListener = context as BankDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface BankDialogListener {
        fun chooseBankClick(bank: NetworkPaySprintDmtBanksData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}