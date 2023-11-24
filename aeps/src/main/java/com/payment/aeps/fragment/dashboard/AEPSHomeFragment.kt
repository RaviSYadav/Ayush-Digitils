package com.payment.aeps.fragment.dashboard

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.viewbinding.viewBinding
import com.payment.aeps.R
import com.payment.aeps.base.AepsBaseFragment
import com.payment.aeps.databinding.FragmentAepshomeBinding
import com.payment.aeps.preferences.AepsPrefRepository


/**
 * Created by Ravi Yadav <> on 28/08/23.
 * Safepe pvt
 *
 */
class AEPSHomeFragment : Fragment(R.layout.fragment_aepshome),AEPSHomeController.AdapterCallbacks {


    val binding by viewBinding(FragmentAepshomeBinding::bind)

    private val controller: AEPSHomeController by lazy { AEPSHomeController(this) }
    private lateinit var prefRepository : AepsPrefRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefRepository = AepsPrefRepository(requireContext())
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initClick()
        controller.setData(AEPSHomeData.aepsDashboard)
        val selectedDevice = prefRepository.getSelectedDevice()

        if (!selectedDevice.isNullOrEmpty()){
            val device = "Selected Device  -  $selectedDevice"
            binding.lblDevice.text = device
        }else{
            deviceSelection()
        }





    }
    private fun deviceSelection(){
        val deviceType = arrayOf("Mantra", "Morpho", "Tatvik", "Startek", "SecuGen", "Evolute")
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Please select one option")
        alert.setSingleChoiceItems(deviceType, -1) { _: DialogInterface?, which: Int ->

            prefRepository.setSelectedDevice(deviceType[which])
            prefRepository.setSelectedDeviceIndex(which.toString())


            binding.lblDevice.text = "Selected Device  -  ${deviceType[which]}"
        }

        alert.setPositiveButton(
            "OK"
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        alert.setCancelable(false)
        alert.show()
    }
    private fun initRecyclerView(){
        val spanCount = 2
        val layoutManager = GridLayoutManager(requireContext(), spanCount)

        with(binding.rvAepsDashboard) {
            setController(controller)
            controller.spanCount = spanCount
            layoutManager.spanSizeLookup = controller.spanSizeLookup
            this.layoutManager = layoutManager
            setItemSpacingDp(8)
        }
    }

    private fun initClick(){
        binding.lblDevice.setOnClickListener {
            deviceSelection()
        }
        binding.lblDescription.setOnClickListener {
            binding.lblDevice.performClick()
        }

    }
    override fun onAepsItemClicked(aepsDashboard: AEPSDashboard, position: Int) {

        findNavController().navigate(AEPSHomeFragmentDirections.actionAEPSHomeFragmentToCashWithdrawFragment(aepsDashboard.title,aepsDashboard.transaction_type))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                val status = data?.getBooleanExtra("status", false)
                val response = data?.getIntExtra("response", 0)
                val message = data!!.getStringExtra("message")

                val detailedResponse = "Status: $status,  Response: $response, Message: $message"

//                showToast(detailedResponse)
            }
        }
    }



}