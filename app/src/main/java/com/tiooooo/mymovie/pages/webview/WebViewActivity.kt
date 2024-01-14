package com.tiooooo.mymovie.pages.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.mymovie.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    companion object {
        const val BASE_URL = "BASE_URL"
        const val EXTRA_TITLE = "EXTRA_TITLE"
    }

    override fun getViewBinding() = ActivityWebViewBinding.inflate(layoutInflater)
    private var baseUrl = ""

    override fun initView() {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Detail review"
        binding.toolbar.title = title
        setupToolbar(binding.toolbar)
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        baseUrl = intent.getStringExtra(BASE_URL) ?: ""
        binding.webView.apply {
            webViewClient = webClient
            setUpWebViewSettings(binding.webView)
            loadUrl(baseUrl)
        }
    }

    private val webClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBar.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebViewSettings(view: WebView) {
        view.apply {
            clearCache(true)
            settings.let {
                it.domStorageEnabled = true
                it.loadsImagesAutomatically = true
                it.loadWithOverviewMode = true
                it.javaScriptEnabled = true
                it.builtInZoomControls = true
                it.useWideViewPort = true
                it.displayZoomControls = false
                it.mediaPlaybackRequiresUserGesture = false
                it.setSupportZoom(false)
            }
        }
    }
}
