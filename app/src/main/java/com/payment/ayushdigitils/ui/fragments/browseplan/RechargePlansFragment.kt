package com.payment.ayushdigitils.ui.fragments.browseplan

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkRechargePlansData
import com.payment.ayushdigitils.databinding.FragmentRechargePlansBinding
import com.payment.ayushdigitils.persistence.Prefs
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class RechargePlansFragment(
    type: String,
    mobile: String,
    provider_id: String,
    provider_name: String,
    listener: PlansDialogListener
) : BottomSheetDialogFragment(R.layout.fragment_recharge_plans),RechargePlansAdapter.OnPlanClickListener {

    private var _binding: FragmentRechargePlansBinding? = null
    private val binding get() = _binding!!

    val prefs: Prefs by inject()
    private val viewModel by viewModel<RechargePlansViewModel>()

    private lateinit var adapter_: RechargePlansAdapter

    private var mBottomSheetListener: PlansDialogListener? = null

    private var type = ""
    private var mobile = ""
    private var provider_id = ""
    private var provider_name = ""


    init {
        this.type = type
        this.mobile = mobile
        this.provider_id = provider_id
        this.provider_name = provider_name

        this.mBottomSheetListener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargePlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.fragment_recharge_plans)

        // Set the full-screen style
        val bottomSheetBehavior = dialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 0

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()

    }

    fun initView() {
        adapter_ = RechargePlansAdapter(this)
        binding.planList.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = adapter_
        }
        getRechargePlans()
        observer()
    }

    fun initClick() {
        binding.etSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter_.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun observer() {

        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.getRechargePlans.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("dmt registration Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false

                        val planDataMap = data.data
                        val tabNames = planDataMap?.keys?.toList()
                        binding.tabLayout.removeAllTabs()
                        tabNames?.forEach { tabName ->
                            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabName))
                        }

                        // Handle tab selection
                        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                binding.etSearch.setText("")
                                tab?.let {
                                    val selectedTabName = tabNames?.get(it.position)
                                    val selectedPlans = planDataMap?.get(selectedTabName) ?: emptyList()
                                    adapter_.setPlans(selectedPlans)
                                }
                            }

                            override fun onTabUnselected(tab: TabLayout.Tab?) {}
                            override fun onTabReselected(tab: TabLayout.Tab?) {}
                        })

                        // Initialize the RecyclerView with the first tab's data
                        val initialTabName = tabNames?.firstOrNull()
                        initialTabName?.let {
                            val initialPlans = planDataMap[it] ?: emptyList()
                            adapter_.setPlans(initialPlans)
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

    private fun getRechargePlans() {
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("type", type)
            put("number", mobile)
            put("providername", provider_id)
        }

        viewModel.requestRechargePlans.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }


    interface PlansDialogListener {
        fun choosePlansClick(amount: String)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onButtonSelect(position: Int, plan: NetworkRechargePlansData) {

        mBottomSheetListener?.choosePlansClick(plan.rs ?: "0")
    }

    override fun onImgExpand(position: Int, plan: NetworkRechargePlansData) {
    }


}