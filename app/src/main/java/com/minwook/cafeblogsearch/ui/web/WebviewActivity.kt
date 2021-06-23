package com.minwook.cafeblogsearch.ui.web

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.minwook.cafeblogsearch.RxBus
import com.minwook.cafeblogsearch.databinding.ActivityWebviewBinding
import com.minwook.cafeblogsearch.ui.main.MainViewModel
import com.minwook.cafeblogsearch.ui.main.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebviewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "EXTRA_URL"
        const val EXTRA_TITLE = "EXTRA_TITLE"
    }

    private lateinit var binding: ActivityWebviewBinding
    private var url: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initExtra()
        initView()
    }

    private fun initExtra() {
        if (intent.hasExtra(EXTRA_URL)) {
            url = intent.getStringExtra(EXTRA_URL)
        }

        if (intent.hasExtra(EXTRA_TITLE)) {
            title = intent.getStringExtra(EXTRA_TITLE)
        }
    }

    private fun initView() {
        binding.apply {
            tbWeb.title = HtmlCompat.fromHtml(title ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            setSupportActionBar(tbWeb)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            wbWeb.webChromeClient = WebChromeClient()
            wbWeb.webViewClient = WebViewClient()
            wbWeb.settings.javaScriptEnabled = true

            url?.let {
                wbWeb.loadUrl(it)
                
                //웹 페이지 확인
                RxBus.publish(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}