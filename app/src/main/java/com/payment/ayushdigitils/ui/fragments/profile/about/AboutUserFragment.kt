package com.payment.ayushdigitils.ui.fragments.profile.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentAboutuserBinding
import com.payment.ayushdigitils.ui.base.BaseFragment


class AboutUserFragment : BaseFragment(R.layout.fragment_aboutuser) {

    private var _binding : FragmentAboutuserBinding?= null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutuserBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }


    override fun initView() {
        binding.lblUserName.text = prefs.getUserName()
        binding.lblMobile.text = prefs.getUserContact()
        binding.lblEmail.text = prefs.getUserEmail()
        binding.lblAddress.text = prefs.getAddress()
    }

    override fun initClick() {
    }





}