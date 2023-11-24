package com.payment.ayushdigitils.ui.fragments.dmt.validation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentDmtotpBinding
import com.payment.ayushdigitils.ui.utils.Constanse.configOtpEditText
import com.payment.ayushdigitils.ui.utils.Constanse.makeLinks


class DMTOtpFragment : Fragment(R.layout.fragment_dmtotp) {

    private var _binding : FragmentDmtotpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDmtotpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resendOtp()

        configOtpEditText(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )
    }
    private fun resendOtp(){
        binding.lblResendOtp.makeLinks(
            Pair("Resend OTP", View.OnClickListener {
                Toast.makeText(requireContext(), "OTP Resend successfully", Toast.LENGTH_SHORT).show()
            })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}