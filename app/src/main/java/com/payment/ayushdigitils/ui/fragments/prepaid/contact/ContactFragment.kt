package com.payment.ayushdigitils.ui.fragments.prepaid.contact

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPaySprintDmtBanksData
import com.payment.ayushdigitils.databinding.FragmentContactBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactFragment(listener: ContactDialogListener) : BottomSheetDialogFragment(R.layout.fragment_contact),ContactAdapter.OnContactClickListener {

    private var _binding: FragmentContactBinding?= null
    private val binding get() = _binding!!



    private val viewModel by viewModel<ContactViewModel>()
    private lateinit var adapter: ContactAdapter

    private var listener:ContactDialogListener?= null

    init {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentContactBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.fragment_contact)

        // Set the full-screen style
        val bottomSheetBehavior = dialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 0

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()

    }
     fun initView() {
        prepareRecyclerView()
         viewModel.fetchContacts()

    }

     fun initClick() {
         binding.etSearch.addTextChangedListener(object : TextWatcher{
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

             }

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 adapter.filter.filter(s.toString())
             }

             override fun afterTextChanged(s: Editable?) {
             }

         })
    }
    private fun prepareRecyclerView() {
        adapter = ContactAdapter(this)
        binding.rvContact.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ContactFragment.adapter
        }

    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
        viewModel.contactList.observe(viewLifecycleOwner, Observer { contacts ->
            viewModel.showLoader.value = false
            if (contacts.isNotEmpty()) {
                adapter.setContacts(contacts)
            }else{
                Toast.makeText(requireContext(), "Yoo Don't have any contact", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
        })


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /** attach listener from parent fragment */
        try {
            listener = context as ContactDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface ContactDialogListener {
        fun contactClick(contact: ContactModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onContactClicked(position: Int, contact: ContactModel) {
        listener?.contactClick(contact)

    }
}