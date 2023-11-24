package com.payment.ayushdigitils.ui.fragments.report.filter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentFiltersBinding
import com.payment.ayushdigitils.persistence.Prefs
import com.payment.ayushdigitils.ui.utils.Constanse.SHOWING_DATE_FORMAT
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import java.util.Date
import java.util.GregorianCalendar


class FiltersFragment(listener:FiltersDialogListener,selectStatus:String,selectFromDate:String,selectToDate:String) : BottomSheetDialogFragment(R.layout.fragment_filters) {

    private var _binding : FragmentFiltersBinding?= null
    private val binding get() = _binding!!
    val prefs: Prefs by inject()

    private var listener:FiltersDialogListener?= null
    private var selectStatus:String= ""
    private var selectFromDate:String= ""
    private var selectToDate:String= ""


    init {

        this.listener = listener
        this.selectStatus = selectStatus
        this.selectFromDate = selectFromDate
        this.selectToDate = selectToDate
    }


    private  var status:String? = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.fragment_filters)

        // Set the full-screen style
        val bottomSheetBehavior = dialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 0

        bottomSheetBehavior.isDraggable = false
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }
    private fun initView(){
        if (!selectStatus.isNullOrEmpty() || !selectStatus.equals("")){
            when(selectStatus){
               "success" ->binding.rbSuccess.isChecked = true
               "pending" ->binding.rbPending.isChecked = true
               "failed" ->binding.rbFailed.isChecked = true
               "accept" ->binding.rbAccept.isChecked = true
               "reversed" ->binding.rbReversed.isChecked = true
               "refunded" ->binding.rbRefunded.isChecked = true
            }
        }

        if (!selectFromDate.isNullOrEmpty() || !selectFromDate.equals("")){
            binding.tvFromDate.setText(selectFromDate)
        }
        if (!selectToDate.isNullOrEmpty() || !selectToDate.equals("")){
            binding.tvToDate.setText(selectToDate)
        }
    }
    private fun initClick(){
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = binding.rgStatus.findViewById<RadioButton>(checkedId)
            if (selectedRadioButton != null) {
                 status = selectedRadioButton.text.toString().lowercase()
                // Do something with the selected option
                // For example, display a toast with the selected option
                // Toast.makeText(this, "Selected option: $selectedOption", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnApply.setOnClickListener {
            listener?.onFilterClick(status = status, fromDate = binding.tvFromDate.text.toString(), toDate = binding.tvToDate.text.toString())
        }

        binding.tvFromDate.setOnClickListener {
            datePopUp(1)
        }
        binding.tvToDate.setOnClickListener {
            datePopUp(2)
        }

        binding.lblClear.setOnClickListener {
            binding.tvFromDate.text = ""
            binding.tvToDate.text = ""
            binding.rgStatus.clearCheck()
        }
    }


    private fun datePopUp(count: Int) {
        val adb = AlertDialog.Builder(requireContext())
        val view: View = LayoutInflater.from(requireContext()).inflate(R.layout.date_picker, null)
        adb.setView(view)
        val dialog: Dialog
        adb.setPositiveButton(
            "Add"
        ) { dialog1: DialogInterface?, arg1: Int ->
            val etDatePicker =
                view.findViewById<DatePicker>(R.id.lbl_datePicker)
            val cal = GregorianCalendar.getInstance()
            cal[etDatePicker.year, etDatePicker.month] = etDatePicker.dayOfMonth
            var date: Date? = null
            date = cal.time
            val approxDateString: String = SHOWING_DATE_FORMAT.format(date)
            when (count) {
                1 -> binding.tvFromDate.setText(approxDateString)
                2 -> binding.tvToDate.setText(approxDateString)
            }
        }
        dialog = adb.create()
        dialog.show()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        /** attach listener from parent fragment */
        try {
            listener = context as FiltersDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface FiltersDialogListener {
        fun onFilterClick(status:String?,fromDate:String,toDate:String)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}