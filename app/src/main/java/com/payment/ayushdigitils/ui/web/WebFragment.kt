package com.payment.ayushdigitils.ui.web

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class WebFragment : BaseFragment(R.layout.fragment_web) {

    private val args: WebFragmentArgs by navArgs()
    private var webView: WebView? = null
    private val viewModel by viewModel<WebViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

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
        Timber.d("ARGS: URL: ${args.url}")
        webView?.loadUrl(args.url)
    }

    override fun initView() {}

    override fun initClick() {}
}