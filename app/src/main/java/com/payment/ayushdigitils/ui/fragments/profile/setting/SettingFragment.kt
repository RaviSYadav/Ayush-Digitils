package com.payment.ayushdigitils.ui.fragments.profile.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.payment.ayushdigitils.NavGraphWebDirections
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.SplashActivity
import com.payment.ayushdigitils.databinding.FragmentSettingBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.profile.ProfileFragmentDirections
import com.payment.ayushdigitils.vo.Resource
import com.shreyaspatil.MaterialDialog.MaterialDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SettingFragment : BaseFragment(R.layout.fragment_setting) {

    private var _binding:FragmentSettingBinding?= null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SettingsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()
    }
    override fun initView() {
    }

    override fun initClick() {
        binding.lblChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }
        binding.lblChangeTpin.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangeTPinFragment()
            findNavController().navigate(action)
        }

        binding.lblFaqs.setOnClickListener {
            val url = ""

            if (url.isNotEmpty()) {
                val action = NavGraphWebDirections.actionGlobalWebFragment("")
                findNavController().navigate(action)
            }else{
                viewModel.toastMsg.value = "Please Provide FAQ's url"
            }
        }

        binding.lblPrivacyPolicy.setOnClickListener {
            val url = "https://login.ayusdigital.co.in/privecy-policy"

            if (url.isNotEmpty()) {
                val action = NavGraphWebDirections.actionGlobalWebFragment(url)
                findNavController().navigate(action)
            }else{
                viewModel.toastMsg.value = "Please Provide Privacy Policy url"
            }
        }

        binding.lblTerms.setOnClickListener {
            val url = "https://login.ayusdigital.co.in/terms-condition"

            if (url.isNotEmpty()) {
                val action = NavGraphWebDirections.actionGlobalWebFragment(url)
                findNavController().navigate(action)
            }else{
                viewModel.toastMsg.value = "Please Provide Terms url"
            }
        }

        binding.lblLogout.setOnClickListener {
            logoutConfirmation()
        }
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}