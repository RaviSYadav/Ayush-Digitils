package com.payment.ayushdigitils.ui.fragments.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentSuccessBinding
import com.payment.ayushdigitils.ex.captureAndSharePdfScreenshot
import com.payment.ayushdigitils.ui.base.BaseFragment


class SuccessFragment : BaseFragment(R.layout.fragment_success) {
    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    private val args: SuccessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()

    }

    override fun initView() {
        binding.lblRefHeading.text = "Txn ID"
        binding.lblSenderHeading.text = "RRN No"
        args.recharge?.let {
            binding.lblRefNum.text = it.txnid
            binding.lblSenderName.text = it.rrn
        }
        binding.lblAmount.text = getString(
            R.string.remitter_full_name,
            resources.getString(R.string.Rs),
            args.amount
        )
        binding.lblPayMethodType.text = args.transectionType
        binding.lblPaymentTime.text = args.date
    }

    override fun initClick() {

        binding.lblPdfDownload.setOnClickListener {
            binding.containerInvoice.captureAndSharePdfScreenshot()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}