package com.payment.aeps.base


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.payment.aeps.preferences.AepsPrefRepository

import kotlinx.coroutines.*

abstract class AepsBaseFragment(layout: Int) : Fragment(layout){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }






    var progressBarVisibility: ShowProgressBar? = null


















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
