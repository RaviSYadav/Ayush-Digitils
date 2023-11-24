package com.payment.ayushdigitils.ui.base

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.payment.ayushdigitils.persistence.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext



abstract class BaseDialogFragment(layout: Int) : DialogFragment(layout), CoroutineScope {


    private val viewModel by viewModel<BaseViewModel>()

    val prefs: Prefs by inject()





    var progressBarVisibility: ShowProgressBar? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    abstract fun initView()
    abstract fun initClick()

    // region [ Authentication Process ]


















    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShowProgressBar) {
            progressBarVisibility = context
        } else {
            throw RuntimeException("$context must implement ShowProgressBar")
        }
    }

    interface ShowProgressBar {
        fun setVisibility(visibility: Int)
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        progressBarVisibility = null
    }

}