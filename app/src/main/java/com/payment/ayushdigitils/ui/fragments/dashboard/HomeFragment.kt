package com.payment.ayushdigitils.ui.fragments.dashboard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.payment.ayushdigitils.BuildConfig
import com.example.ui.dashboard.banner.SliderItems
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.SplashActivity
import com.payment.ayushdigitils.databinding.FragmentHomeBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.dashboard.banner.BannerAdapter
import com.payment.ayushdigitils.ui.fragments.dashboard.controller.DashboardController
import com.payment.ayushdigitils.ui.fragments.dashboard.section.Balance
import com.payment.ayushdigitils.vo.Resource
import com.payment.aeps.AEPSHomeActivity
import com.paysprint.onboardinglib.activities.HostActivity
import com.shreyaspatil.MaterialDialog.MaterialDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.abs


class HomeFragment : BaseFragment(R.layout.fragment_home), DashboardController.AdapterCallbacks {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and

    // onDestroyView.
    private val binding get() = _binding!!


    private val sliderHandler: Handler = Handler()

    private val viewModel by viewModel<HomeViewModel>()
    private val controller: DashboardController by lazy { DashboardController(this) }
    override fun initView() {

        Log.v("app token", "====${prefs.getUserId()}====${prefs.getAppToken()}")

        Glide.with(binding.toolbar.imgProfile)
            .load(
                AvatarGenerator.avatarImage(
                    requireContext(),
                    90,
                    AvatarConstants.CIRCLE,
                    prefs.getUserName()
                )
            )
            .into(binding.toolbar.imgProfile)
        binding.toolbar.lblName.text = prefs.getUserName()
        binding.toolbar.lblUserId.text =
            getString(R.string.remitter_full_name, "id :", prefs.getUserId().toString())

    }

    override fun initClick() {
        binding.toolbar.imgLogout.setOnClickListener {
            logoutConfirmation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initView()
        initClick()
        prepareData()
        setBanner()
    }

    private fun setBanner() {
        val sliderItems: MutableList<SliderItems> = ArrayList()
        sliderItems.add(SliderItems(R.mipmap.image1))
        sliderItems.add(SliderItems(R.mipmap.image1))
        sliderItems.add(SliderItems(R.mipmap.image1))
        sliderItems.add(SliderItems(R.mipmap.image1))
        sliderItems.add(SliderItems(R.mipmap.image1))


        binding.viewPager.apply {
            adapter = BannerAdapter(sliderItems, binding.viewPager)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//            setPageTransformer(SliderTransformer(3))
        }


        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.viewPager.setPageTransformer(compositePageTransformer)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 2000) // slide duration 2 seconds
            }

        })
    }

    private val sliderRunnable = Runnable {
        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
    }


    private fun updateController(balance: List<Balance>, sections: List<Section>) {
        controller.setData(balance, sections)

    }

    private fun prepareData() {
        binding.rvDashboard.setController(controller)

        val balance by lazy {
            listOf(
                Balance(
                    type = "main_fund",
                    amount = prefs.getMainWallet(),
                    title = "Main Balance"
                )/*,
                Balance(
                    type = "aeps_fund",
                    amount = prefs.getMainWallet(),
                    title = "Aeps Balance"
                )*/
            )
        }
        updateController(balance, DemoData.section1)
    }

    private fun getBalance() {
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
        }

        viewModel.balanceBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


    }

    private fun logout() {
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
        }
        viewModel.logoutBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })

        viewModel.getLogOutResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("login Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false

                        prefs.clear()
                        prefs.setIsAnonymous(true)
                        val intent = Intent(requireActivity(), SplashActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
//                        Runtime.getRuntime().exit(0)


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "home ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
        viewModel.getBalanceResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        when (data.status) {
                            "TXN" -> {
                                data.data?.let { balanceData ->
                                    prefs.setPaySprintBoard(balanceData.pasprintonboard)
                                    prefs.setMainWallet(balanceData.mainwallet.toString())
                                    prefs.setAepsBalance(balanceData.aepsbalance.toString())
                                    prefs.setUpiMerchant(balanceData.upimerchent)
                                    prefs.setPkey(balanceData.pId)
                                    prefs.setApiKey(balanceData.apiKey)
                                    prefs.setApiKey(balanceData.apiKey)
                                    prefs.setLatitude(balanceData.lat.toString())
                                    prefs.setLongitude(balanceData.lon.toString())
                                    prefs.setMCode(balanceData.mCode)
                                    prefs.setFAepsAuth(balanceData.isdoneaepsauth)
                                }
                                val balance by lazy {
                                    listOf(
                                        Balance(
                                            type = "main_fund",
                                            amount = prefs.getMainWallet(),
                                            title = "Main Balance"
                                        ),
                                        /*Balance(
                                            type = "aeps_fund",
                                            amount = prefs.getMainWallet(),
                                            title = "Aeps Balance"
                                        )*/
                                    )
                                }
                                updateController(balance, DemoData.section1)

                            }

                            "UA" -> {
                                logoutWarning()
                            }

                            else -> {
                                viewModel.toastMsg.value = data.message
                            }
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "home ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })

        viewModel.getOnboardingResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("login Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.status == "TXN") {
                            openPSOnboarding()


                        } else {
                            showToast(data.message)
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "home ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
        getBalance()
    }

    override fun onFinanceClicked(dashboard: Dashboard, sectionSlug: String, sectionTitle: String) {
        when (dashboard.slug) {
            "aeps" -> {
                if (prefs.getPaySprintBoard() == "true") {
                    if (prefs.getFAepsAuth() == "false") {
                        aadhaar2FactorWarning()
                    } else {
                        val intent = Intent(requireActivity(), AEPSHomeActivity::class.java)
                        intent.putExtra("userId", prefs.getUserId().toString())
                        intent.putExtra("appToken", prefs.getAppToken())
                        intent.putExtra("aepsType", "PAYSPRINT")
                        intent.putExtra("baseUrl", BuildConfig.BASE_URL)
                        startActivity(intent)
                    }
                } else if (prefs.getPaySprintBoard() == "pending") {
                    showToast("Please Wait For The Approval")

                } else {
                    requestMCode()
                }

            }

            "matm" -> {

                if (prefs.getPaySprintBoard() == "true") {
                    if (prefs.getFAepsAuth() == "false") {
                        aadhaar2FactorWarning()
                    } else {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMATMDashboardFragment())
                    }
                } else if (prefs.getPaySprintBoard() == "pending") {
                    showToast("Please Wait For The Approval")

                } else {
                    requestMCode()
                }


            }

            "upi" -> {
                viewModel.toastMsg.value = "Coming soon"
            }

            "dmt" -> {

                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDMTBottomFragment(
                        "dmt"
                    )
                )
            }
        }


    }

    override fun onRechargeClicked(
        dashboard: Dashboard,
        sectionSlug: String,
        sectionTitle: String
    ) {
        when (dashboard.slug) {
            "prepaid" -> {
                val action = HomeFragmentDirections.actionDashboardToPrepaidRecharge()
                findNavController().navigate(action)

            }

            "dth" -> {
                val action = HomeFragmentDirections.actionHomeFragmentToDTHFragment()
                findNavController().navigate(action)
            }

            "electricity" -> {
                val action = HomeFragmentDirections.actionDashboardToBillOperator(
                    dashboard.slug,
                    dashboard.title
                )
                findNavController().navigate(action)
            }

            else -> {
                val action = HomeFragmentDirections.actionDashboardToBillOperator(
                    dashboard.slug,
                    dashboard.title,
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onTravelsClicked(dashboard: Dashboard, sectionSlug: String, sectionTitle: String) {
        when (dashboard.slug) {
            "fasttag" -> {
                val action = HomeFragmentDirections.actionDashboardToBillOperator(
                    dashboard.slug,
                    dashboard.title
                )
                findNavController().navigate(action)
            }

            "schoolfees" -> {
                val action = HomeFragmentDirections.actionDashboardToBillOperator(
                    dashboard.slug,
                    dashboard.title
                )
                findNavController().navigate(action)
            }

            "lifeinsurance" -> {
                val action = HomeFragmentDirections.actionDashboardToBillOperator(
                    dashboard.slug,
                    dashboard.title
                )
                findNavController().navigate(action)
            }


            else -> {
                val action = HomeFragmentDirections.actionDashboardToMoreComingSoon()
                findNavController().navigate(action)
            }
        }

    }

    override fun onBalanceClicked(balance: Balance, position: Int) {

        if (balance.type == "aeps_fund") {
            val action = HomeFragmentDirections.actionHomeFragmentToAEPSFundFragment()
            findNavController().navigate(action)
        } else {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletFundFragment()
            findNavController().navigate(action)
        }
    }

    override fun onSpecialClicked(dashboard: Dashboard, position: Int) {
        if (dashboard.slug.isNotEmpty()) {

            /*val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dashboard.slug))
            startActivity(intent)*/
        }
    }

    private fun requestMCode() {
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("merchantcode", prefs.getMCode())
        }
        viewModel.onboardingBody.value = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun openPSOnboarding() {
        val intent = Intent(requireContext(), HostActivity::class.java)
        intent.putExtra("pId", prefs.getPkey())
        intent.putExtra("pApiKey", prefs.getApiKey())
        intent.putExtra("pApiKey", prefs.getApiKey())
        intent.putExtra("mCode", prefs.getMCode())
        intent.putExtra("mobile", prefs.getUserContact())
        intent.putExtra("lat", prefs.getLatitude())
        intent.putExtra("lng", prefs.getLongitude())
        intent.putExtra("firm", prefs.getShopName())
        intent.putExtra("email", prefs.getUserEmail())
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 999)

    }

    private fun logoutConfirmation() {
        MaterialDialog.Builder(requireActivity())
            .apply {
                setTitle("Logout?")
                setMessage("Are You sure you want to logout?")
                setCancelable(false)
                setAnimation("log_out.json")
                setPositiveButton("Logout", R.drawable.ic_login) { dialogInterface, which ->
                    logout()
                    dialogInterface.dismiss()
                }
                setNegativeButton(
                    "Cancel",
                    android.R.drawable.ic_delete
                ) { dialogInterface, which ->
                    // Delete Operation
                    dialogInterface.dismiss()
                }
                build().show()
            }

    }

    private fun aadhaar2FactorWarning() {
        MaterialDialog.Builder(requireActivity())
            .apply {
                setTitle("2FA Verification?")
                setMessage("As per regulatory guidelines this is mandatory for Aadhaar related Service,confirm your identify daily for once")
                setCancelable(false)
                setAnimation("aadhaar_2fa.json")
                setPositiveButton("Confirm", R.drawable.iv_verified) { dialogInterface, which ->
                    aadhaar2faNavigation()
                    dialogInterface.dismiss()
                }
                setNegativeButton(
                    "Cancel",
                    android.R.drawable.ic_delete
                ) { dialogInterface, which ->
                    // Delete Operation
                    dialogInterface.dismiss()
                }
                build().show()
            }

    }

    private fun aadhaar2faNavigation() {
        val action = HomeFragmentDirections.actionHomeFragmentToAadhaar2AuthVerificationFragment()

        findNavController().navigate(action)
    }

    private fun logoutWarning() {
        MaterialDialog.Builder(requireActivity())
            .apply {
                setTitle("Authentication!")
                setMessage("Your session has been expired.")
                setCancelable(false)
                setAnimation("log_out.json")
                setPositiveButton("Login", R.drawable.ic_login) { dialogInterface, which ->
                    logout()
                    dialogInterface.dismiss()
                }
                build().show()
            }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                val status = data?.getBooleanExtra("status", false)
                val response = data?.getIntExtra("response", 0)
                val message = data?.getStringExtra("message")
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("Info")
                builder.setMessage(message)
                builder.setCancelable(false)
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.show();
            }
        }
    }

}