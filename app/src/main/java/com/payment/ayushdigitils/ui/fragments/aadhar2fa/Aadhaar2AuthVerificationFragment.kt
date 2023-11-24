package com.payment.ayushdigitils.ui.fragments.aadhar2fa

import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.payment.aeps.AEPSHomeActivity
import com.payment.aeps.utils.AadharNumberPattern
import com.payment.aeps.utils.DeviceDataModel
import com.payment.aeps.utils.ModuleUtil
import com.payment.aeps.utils.utils
import com.payment.ayushdigitils.BuildConfig
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentAadhar2AuthVerificationBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.vo.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Locale
import java.util.regex.Pattern


class Aadhaar2AuthVerificationFragment : BaseFragment(R.layout.fragment_aadhar2_auth_verification) {
    private  var _binding : FragmentAadhar2AuthVerificationBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModel<Aadhaar2FaViewModel>()

    private lateinit var morphoDeviceData: DeviceDataModel
    private var PID_DATA = ""
    var PidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"2\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" wadh=\"\" posh=\"UNKNOWN\" /></PidOptions>"

    private val banks = arrayOf("ICICI", "FINO", "NSDL")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAadhar2AuthVerificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()

    }

    override fun initView() {

        observer()

        val adapterCity: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, banks)
        binding.spinAeps.setAdapter(adapterCity)

    }

    override fun initClick() {
        binding.lblDevice.setOnClickListener {
            deviceSelection()
        }
        binding.lblDescription.setOnClickListener {
            binding.lblDevice.performClick()
        }
        binding.btnSubmit.setOnClickListener {
            if (isValid()){
                scanDevice()
            }
        }
    }

    private fun deviceSelection(){
        val deviceType = arrayOf("Mantra", "Morpho", "Tatvik", "Startek", "SecuGen", "Evolute")
        val alert = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        alert.setTitle("Please select one option")
        alert.setSingleChoiceItems(deviceType, -1) { _: DialogInterface?, which: Int ->

            prefs.setSelectedDevice(deviceType[which])
            prefs.setSelectedDeviceIndex("$which")


            binding.lblDevice.text = "Selected Device  -  ${deviceType[which]}"
        }

        alert.setPositiveButton(
            "OK"
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        alert.setCancelable(false)
        alert.show()
    }
    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            showMsg(it)
        })
        viewModel.getAadhaar2FaResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("balance Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {

                            val intent = Intent(requireActivity(), AEPSHomeActivity::class.java)
                            intent.putExtra("userId", prefs.getUserId().toString())
                            intent.putExtra("appToken", prefs.getAppToken())
                            intent.putExtra("aepsType", "PAYSPRINT")
                            intent.putExtra("baseUrl", BuildConfig.BASE_URL)
                            startActivity(intent)



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
    private fun MorphoDevice() {
        val packageManager: PackageManager = requireActivity().packageManager
        if (ModuleUtil.isPackageInstalled("com.scl.rdservice", packageManager)) {
            val intent = Intent("in.gov.uidai.rdservice.fp.INFO")
            intent.setPackage("com.scl.rdservice")
            this.startActivityForResult(intent, 1)
        } else {
            serviceNotFoundDialog(
                "Get Service", "Morpho RD Services Not Found.Click OK to Download Now.",
                "https://play.google.com/store/apps/details?id=com.scl.rdservice"
            )
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dataModel: DeviceDataModel
        try {
            when (requestCode) {
                1 -> {
                    this.morphoDeviceData =
                        utils().morphoDeviceData(requireActivity(), data!!.getStringExtra("DEVICE_INFO"))!!
                    if (this.morphoDeviceData.errCode.equals("919",true)) {
                        this.morphoFinger()
                    }
                }

                2 -> {
                    dataModel = utils().morphoFingerData(
                        requireActivity(),
                        data!!.getStringExtra("PID_DATA"),
                        this.morphoDeviceData
                    )!!
                    Log.e("ERROR INFO", "=-----------------------------------")
                    //Log.e("ERROR INFO", "" + dataModel.getErrMsg());
                    if (dataModel.errCode.equals("0",true)) {
                        PID_DATA = data.getStringExtra("PID_DATA").toString()
                        callApiWithPidData()
                    } else if (ModuleUtil.getPreferredPackage(requireActivity(), "com.scl.rdservice")
                            .equals("")
                    ) {
                        showMsg(
                            dataModel.errCode + " : Morpho " + dataModel.errMsg + ModuleUtil.getPreferredPackage(
                                requireActivity(),
                                "com.scl.rdservice"
                            )
                        )
                    }
                }

                3 -> {
                    dataModel = utils().mantraData(data!!.getStringExtra("PID_DATA"), requireActivity())
                    if (dataModel.errCode.equals("0",true)) {
                        PID_DATA = data.getStringExtra("PID_DATA")!!
                        callApiWithPidData()
                    } else if (ModuleUtil.getPreferredPackage(requireActivity(), "com.mantra.rdservice")
                            .equals("")
                    ) {
                        showMsg(
                            dataModel.errCode + " : Mantra " + dataModel.errMsg + ModuleUtil.getPreferredPackage(
                                requireActivity(),
                                "com.mantra.rdservice"
                            )
                        )
                    }
                }

                4 -> {
                    dataModel = utils().secugenData(requireActivity(), data!!.getStringExtra("PID_DATA"))
                    if (dataModel.errCode.equals("0")) {
                        PID_DATA = data.getStringExtra("PID_DATA")!!
                        callApiWithPidData()
                    } else if (ModuleUtil.getPreferredPackage(requireActivity(), "com.secugen.rdservice")
                            .equals("")
                    ) {
                        showMsg(
                            dataModel.errCode + " : Sucugen " + dataModel.errMsg + ModuleUtil.getPreferredPackage(
                                requireActivity(),
                                "com.secugen.rdservice"
                            )
                        )
                    }
                }

                5 -> {
                    dataModel = utils().tatvikData(requireActivity(), data!!.getStringExtra("PID_DATA"))
                    if (dataModel.errCode.equals("0")) {
                        PID_DATA = data.getStringExtra("PID_DATA")!!
                        callApiWithPidData()
                    } else if (ModuleUtil.getPreferredPackage(requireActivity(), "com.tatvik.bio.tmf20")
                            .equals("")
                    ) {
                        showMsg(
                            dataModel.errCode + " : Tatvik " + dataModel.errMsg + ModuleUtil.getPreferredPackage(
                                requireActivity(),
                                "com.tatvik.bio.tmf20"
                            )
                        )
                    }
                }

                6 -> {
                    dataModel = utils().starTekData(requireActivity(), data!!.getStringExtra("PID_DATA"))
                    if (dataModel.errCode.equals("0")) {
                        PID_DATA = data.getStringExtra("PID_DATA")!!
                        callApiWithPidData()
                    } else if (ModuleUtil.getPreferredPackage(requireActivity(), "com.acpl.registersdk")
                            .equals("")
                    ) {
                        showMsg(dataModel.errCode + " : Startek " + dataModel.errMsg + ModuleUtil.getPreferredPackage(requireActivity(),
                            "com.acpl.registersdk"
                        )
                        )
                    }
                }

                7 -> {
                    dataModel = utils().EvoluteData(requireActivity(), data!!.getStringExtra("PID_DATA"))
                    if (dataModel.errCode.equals("0")) {
                        PID_DATA = data.getStringExtra("PID_DATA")!!
                        callApiWithPidData()
                    } else if (ModuleUtil.getPreferredPackage(requireActivity(), "com.evolute.rdservice")
                            .equals("")
                    ) {
                        showMsg(
                            dataModel.errCode + " : Evolute" + dataModel.errMsg + ModuleUtil.getPreferredPackage(
                                requireActivity(),
                                "com.evolute.rdservice"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun scanDevice() {
        var indexCount = 50
        val deviceValue: String = prefs.getSelectedDeviceIndex()
        if (deviceValue.isNotEmpty()) {
            indexCount = Integer.parseInt(deviceValue?:"0")
        }
        when (indexCount) {
            0 -> mantraFinger()
            1 -> this.MorphoDevice()
            2 -> tatvikFinger()
            3 -> starTekFinger()
            4 -> secuGenFinger()
            5 -> evoluteFinger()
            else -> showMsg("Something went wrong.Please try again later.")
        }
    }

    private fun morphoFinger() {
        val intent2 = Intent()
        intent2.component =
            ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity")
        intent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
        intent2.putExtra("PID_OPTIONS", PidData)
        this.startActivityForResult(intent2, 2)
    }

    private fun mantraFinger() {
        val packageManager: PackageManager = requireActivity().packageManager
        if (ModuleUtil.isPackageInstalled("com.mantra.rdservice", packageManager)) {
            val intent2 = Intent()
            intent2.component =
                ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity")
            intent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            intent2.putExtra("PID_OPTIONS", PidData)
            this.startActivityForResult(intent2, 3)
        } else {
            serviceNotFoundDialog(
                "Get Service", "Mantra RD Services Not Found.Click OK to Download Now.",
                "https://play.google.com/store/apps/details?id=com.mantra.rdservice"
            )
        }
    }

    private fun secuGenFinger() {
        val packageManager: PackageManager = requireActivity().packageManager
        if (ModuleUtil.isPackageInstalled("com.secugen.rdservice", packageManager)) {
            val intent2 = Intent()
            intent2.component =
                ComponentName("com.secugen.rdservice", "com.secugen.rdservice.Capture")
            intent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            intent2.putExtra("PID_OPTIONS", PidData)
            this.startActivityForResult(intent2, 4)
        } else {
            serviceNotFoundDialog(
                "Get Service",
                "SecuGen RD Services Not Found.Click OK to Download Now.",
                "https://play.google.com/store/apps/details?id=com.secugen.rdservice"
            )
        }
    }

    private fun tatvikFinger() {
        val packageManager: PackageManager = requireActivity().packageManager
        if (ModuleUtil.isPackageInstalled("com.tatvik.bio.tmf20", packageManager)) {
            val intent2 = Intent()
            intent2.component =
                ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity")
            intent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            intent2.putExtra("PID_OPTIONS", PidData)
            this.startActivityForResult(intent2, 5)
        } else {
            serviceNotFoundDialog(
                "Get Service",
                "Tatvik RD Services Not Found.Click OK to Download Now.",
                "https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20"
            )
        }
    }

    private fun starTekFinger() {
        val packageManager: PackageManager = requireActivity().packageManager
        if (ModuleUtil.isPackageInstalled("com.acpl.registersdk", packageManager)) {
            val intent = Intent()
            intent.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            intent.component =
                ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity")
            intent.putExtra("PID_OPTIONS", PidData)
            this.startActivityForResult(intent, 6)
        } else {
            serviceNotFoundDialog(
                "Get Service",
                "Startek RD Services Not Found.Click OK to Download Now.",
                "https://play.google.com/store/apps/details?id=com.acpl.registersdk"
            )
        }
    }

    private fun evoluteFinger() {
        val packageManager: PackageManager = requireActivity().packageManager
        if (ModuleUtil.isPackageInstalled("com.evolute.rdservice", packageManager)) {
            val intent2 = Intent()
            intent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            intent2.component =
                ComponentName("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity")
            intent2.putExtra("PID_OPTIONS", PidData)
            this.startActivityForResult(intent2, 7)
        } else {
            serviceNotFoundDialog(
                "Get Service",
                "Evolute RD Services Not Found.Click OK to Download Now.",
                "https://play.google.com/store/apps/details?id=com.evolute.rdservice"
            )
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun serviceNotFoundDialog(title: String, msg: String, url: String) {
        val alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton(
            "OK"
        ) { dialog: DialogInterface?, which: Int ->
            try {
                startActivity(
                    Intent(
                        "android.intent.action.VIEW",
                        Uri.parse(url)
                    )
                )
            } catch (var4: java.lang.Exception) {
                showMsg("Something went wrong.Please try again later.")
                var4.printStackTrace()
            }
        }
        alertDialog.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        alertDialog.show()
    }

    private fun validateAadhaarNumber(aadhaarNumber: String): Boolean {
        val aadhaarPattern = Pattern.compile("\\d{12}")
        var isValidAadhar = aadhaarPattern.matcher(aadhaarNumber).matches()
        if (isValidAadhar) {
            isValidAadhar = AadharNumberPattern.validateVerhoeff(aadhaarNumber)
        }
        return isValidAadhar
    }

    private fun callApiWithPidData(){

        Log.v("PID_DATA","------$PID_DATA")

        var indexCount = 50
        val deviceValue: String =
            prefs.getSelectedDeviceIndex()
        if (deviceValue.isNotEmpty()) {
            indexCount = deviceValue.toInt()
        }


        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("adhaarNumber", binding.etAadharNumber.text.toString())
            put("pipe", getBankPosition())
            put("mybiodata", PID_DATA)

            if (indexCount == 0) {
                put("device", "MANTRA_PROTOBUF")
            } else {
                put("device", "MORPHO_PROTOBUF")
            }

//            put("txtPidData", PID_DATA)
        }
        viewModel.request2FBody.value= jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun isValid(): Boolean {

        if (binding.etAadharNumber.text.toString().isEmpty() || !validateAadhaarNumber(binding.etAadharNumber.text.toString())
        ) {
            Toast.makeText(requireActivity(), "Please provide valid aadhhar number", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.spinAeps.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please Select Aeps", Toast.LENGTH_SHORT).show()
            return false
        } else if (getBankPosition().isEmpty()) {
            Toast.makeText(requireContext(), "Please Select Aeps", Toast.LENGTH_SHORT).show()
            return false
        } else if (!binding.checkBoxAadhaar.isChecked()) {
            Toast.makeText(
                requireContext(),
                "Please Accept Aadhaar Consent",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }
    private fun getBankPosition(): String {
        if (binding.spinAeps.text.toString().lowercase(Locale.getDefault())
                .equals("ICICI", ignoreCase = true)
        ) {
            return "bank1"
        }
        if (binding.spinAeps.text.toString().lowercase(Locale.getDefault())
                .equals("FINO", ignoreCase = true)
        ) {
            return "bank2"
        }
        return if (binding.spinAeps.text.toString().lowercase(Locale.getDefault())
                .equals("NSDL", ignoreCase = true)
        ) {
            "bank3"
        } else {
            ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}