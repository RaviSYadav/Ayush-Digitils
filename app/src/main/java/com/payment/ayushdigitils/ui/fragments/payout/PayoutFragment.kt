package com.payment.ayushdigitils.ui.fragments.payout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentPayoutBinding
import com.payment.ayushdigitils.ui.base.BaseFragment


class PayoutFragment : BaseFragment(R.layout.fragment_payout) {

    private var _binding : FragmentPayoutBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {

    }

    override fun initClick() {

    }

}