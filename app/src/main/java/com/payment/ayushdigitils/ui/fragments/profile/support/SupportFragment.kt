package com.payment.ayushdigitils.ui.fragments.profile.support

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentSupportBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.web.WebFragmentArgs
import com.payment.ayushdigitils.ui.web.WebViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SupportFragment : BaseFragment(R.layout.fragment_support) {

    private var webView: WebView? = null
    private val viewModel by viewModel<WebViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = FrameLayout(requireContext())
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        lp.marginStart = 0

        webView = WebView(requireContext())
        webView?.apply {
            webViewClient = WebViewClient()
            settings.apply {
                loadWithOverviewMode = true
                useWideViewPort = true
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                domStorageEnabled = true
            }
        }



        root.addView(webView, lp)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })

        webView?.webViewClient = object : WebViewClient(){


            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                viewModel.showLoader.value = true
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                viewModel.showLoader.value = false
                super.onPageFinished(view, url)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val url = "https://login.ayusdigital.co.in/contact-us"
        Timber.d("ARGS: URL: ${url}")
        webView?.loadUrl(url)
    }

    override fun initView() {}

    override fun initClick() {}
}