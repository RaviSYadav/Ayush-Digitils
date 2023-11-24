package com.payment.aeps.fragment.cash

import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.crazylegend.viewbinding.viewBinding
import com.google.android.material.chip.Chip
import com.payment.aeps.R
import com.payment.aeps.base.AepsBaseFragment
import com.payment.aeps.databinding.FragmentCashwithdrawBinding
import com.payment.aeps.fragment.bank.BanksFragment
import com.payment.aeps.preferences.AepsPrefRepository
import com.payment.aeps.remote.NetworkAepsBank
import com.payment.aeps.utils.AadharNumberPattern
import com.payment.aeps.utils.DeviceDataModel
import com.payment.aeps.utils.ModuleUtil
import com.payment.aeps.utils.manageAepsClick
import com.payment.aeps.utils.utils
import org.json.JSONObject
import java.util.Locale
import java.util.regex.Pattern


class CashWithdrawFragment : AepsBaseFragment(R.layout.fragment_cashwithdraw), BanksFragment.BankDialogListener {

    val binding by viewBinding(FragmentCashwithdrawBinding::bind)
    private  val cashvm  by viewModels<CashWithdrawViewModel>()

    private val args: CashWithdrawFragmentArgs by navArgs()

    lateinit var dialog: BanksFragment /** BottomSheetDialogFragment for choose card */
    lateinit var listener: BanksFragment.BankDialogListener

    private lateinit var morphoDeviceData: DeviceDataModel

    private val banks = arrayOf("ICICI", "FINO", "NSDL")

    private var PID_DATA = ""


    var PidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>"
    private lateinit var prefRepository : AepsPrefRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
        prefRepository = AepsPrefRepository(requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lblHeader.text = args.aepsDashboard
        observer()
        initClick()

        val adapterCity: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, banks)
        binding.spinAeps.setAdapter(adapterCity)

        if (args.transactionType =="BE"){
            binding.tilAmountNum.gone()
            binding.chipGroupScroll.gone()
        }else if (args.transactionType =="MS"){
            binding.tilAmountNum.gone()
            binding.chipGroupScroll.gone()

        }

        createChipView(WithdrawAmountData.amountDataList)


        val deviceIndex = prefRepository.getSelectedDeviceIndex()

        if (deviceIndex.isNullOrEmpty()) {
            showToast("Scanner device selection is required")

        }




    }

    private fun createChipView(languages: List<WithdrawAmountDataList>) {

        binding.chipGroup.isSingleSelection = true

        if (languages.isNullOrEmpty()) {
            binding.chipGroup.gone()
        } else {

            binding.chipGroup.removeAllViews()
            languages.forEach {
                val chip = layoutInflater.inflate(
                    R.layout.layout_chip_choice,
                    binding.chipGroup,
                    false
                ) as Chip

                with(chip) {
                    text = "${it.amount}"
                    tag = it.uniqueSlug
                    chipStrokeWidth = 1.0F
//                    setTextAppearance(R.style.ChipText)
                    isCheckable = true
                    // Add to group before setting it to checked
                    binding.chipGroup.addView(this)
                    setOnClickListener{it2->
                        if (isChecked){
                            binding.etAmount.setText(it2.tag.toString())
                        }

                    }



                }
            }

        }
    }
    private fun initClick(){
        binding.containerBank.setOnClickListener {
            dialog = BanksFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")

        }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRecharge.setOnClickListener {

           /* var indexCount = 50
            val deviceValue: String =
                prefRepository.getSelectedDeviceIndex()
            if (deviceValue.isNotEmpty()) {
                indexCount = deviceValue.toInt()
            }


            val jsonObject = JSONObject().apply {
                put("user_id", prefRepository.getUserId())
                put("apptoken", prefRepository.getAppToken())
                put("transactionType", args.transactionType)
                put("mobileNumber", "7753047402")
                put("adhaarNumber", "496421343376")
                put("bankName1", binding.lblBank.tag.toString())
                put("bankid", binding.lblBank.tag.toString())


                if (args.transactionType == "M")
                    put("bankName2", binding.lblBank.tag.toString())

                if (args.transactionType == "CW" || args.transactionType == "M")
                    put("transactionAmount", binding.etAmount.getText().toString()
                    ) //in case CW/M


                if (indexCount == 0) {
                    put("device", "MANTRA_PROTOBUF")
                } else {
                    put("device", "MORPHO_PROTOBUF")
                }

                put("txtPidData", pidDemo)
            }
            Log.v("getAepsData","===$jsonObject")
            cashvm.getAepsData(jsonObject)*/

            if (isValid()){
                scanDevice()
            }

        }

    }
    private fun observer() {

        cashvm.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
            binding.btnRecharge.manageAepsClick(it)
        })
        cashvm.observeAepsLiveData().observe(viewLifecycleOwner, Observer { aepsData ->
                Log.v("aepsData", "----${aepsData.status}")
                val action = CashWithdrawFragmentDirections.actionCashWithdrawFragmentToAEPSInvoiceFragment(aepsData)
                findNavController().navigate(action)
                cashvm.showLoader.value = false




        })
    }

    private fun scanDevice() {
        var indexCount = 50
        val deviceValue: String =
            prefRepository.getSelectedDeviceIndex()
        if (deviceValue.isNotEmpty()) {
            indexCount = deviceValue.toInt()
        }
        when (indexCount) {
            0 -> MantraFinger()
            1 -> this.MorphoDevice()
            2 -> TatvikFinger()
            3 -> StarTekFinger()
            4 -> SecuGenFinger()
            5 -> EvoluteFinger()
            else -> showMsg("Something went wrong.Please try again later.")
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
                        this.MorphoFinger()
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

    private fun MorphoFinger() {
        val intent2 = Intent()
        intent2.component =
            ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity")
        intent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
        intent2.putExtra("PID_OPTIONS", PidData)
        this.startActivityForResult(intent2, 2)
    }

    private fun MantraFinger() {
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

    private fun SecuGenFinger() {
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

    private fun TatvikFinger() {
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

    private fun StarTekFinger() {
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

    private fun EvoluteFinger() {
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



    override fun chooseBankClick(bank: NetworkAepsBank) {
        binding.lblBank.text = bank.bankName
        binding.lblBank.tag = bank.iinno

        dialog.dismiss()
    }

    private fun callApiWithPidData(){

        Log.v("PID_DATA","------$PID_DATA")

        var indexCount = 50
        val deviceValue: String =
            prefRepository.getSelectedDeviceIndex()
        if (deviceValue.isNotEmpty()) {
            indexCount = deviceValue.toInt()
        }


        val jsonObject = JSONObject().apply {
            put("user_id", prefRepository.getUserId())
            put("apptoken", prefRepository.getAppToken())
            put("transactionType", args.transactionType)
            put("mobileNumber", binding.etMobileNum.text.toString())
            put("adhaarNumber", binding.etAadhaarNum.text.toString())
            put("bankName1", binding.lblBank.tag.toString())
            put("pipe", getBankPosition())
            put("bankid", binding.lblBank.tag.toString())


            if (args.transactionType == "M")
                put("bankName2", binding.lblBank.tag.toString())

            if (args.transactionType == "CW" || args.transactionType == "M")
                put("transactionAmount", binding.etAmount.text.toString()
                ) //in case CW/M


            if (indexCount == 0) {
                put("device", "MANTRA_PROTOBUF")
            } else {
                put("device", "MORPHO_PROTOBUF")
            }

            put("txtPidData", PID_DATA)
        }
        Log.v("getAepsData","===$jsonObject")
        cashvm.getAepsData(jsonObject)
    }


    private fun isValid(): Boolean {
        if (binding.lblBank.tag.toString().isEmpty()) {
            Toast.makeText(requireActivity(), "Bank selection is required", Toast.LENGTH_SHORT).show()
            return false
        }
         if (binding.spinAeps.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please Select Aeps", Toast.LENGTH_SHORT).show()
            return false
        }
          if (getBankPosition().isEmpty()) {
             Toast.makeText(requireContext(), "Please Select Aeps", Toast.LENGTH_SHORT).show()
             return false
         }
        if (binding.etAadhaarNum.text.toString().isEmpty() || !validateAadhaarNumber(binding.etAadhaarNum.text.toString())
        ) {
            Toast.makeText(requireActivity(), "Please provide valid aadhhar number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etMobileNum.text.toString().length != 10) {
            Toast.makeText(requireContext(), "Please provide valid mobile number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (args.transactionType == "CW" || args.transactionType == "M") {
            if (binding.etAmount.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "Please input valid amount string", Toast.LENGTH_SHORT).show()
                return false
            } else if (binding.etAmount.text.toString().toDouble() < 100) {
                Toast.makeText(requireActivity(), "Minimum amount should be 100", Toast.LENGTH_SHORT).show()
                //return false;
            }
        }
        return true
    }
    private fun validateAadhaarNumber(aadhaarNumber: String): Boolean {
        val aadhaarPattern = Pattern.compile("\\d{12}")
        var isValidAadhar = aadhaarPattern.matcher(aadhaarNumber).matches()
        if (isValidAadhar) {
            isValidAadhar = AadharNumberPattern.validateVerhoeff(aadhaarNumber)
        }
        return isValidAadhar
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

}