package com.minwook.cafeblogsearch.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.minwook.cafeblogsearch.data.SearchItem
import com.minwook.cafeblogsearch.databinding.ActivityDetailBinding
import com.minwook.cafeblogsearch.ui.web.WebviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    private lateinit var binding: ActivityDetailBinding
    private var searchData: SearchItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initExtra()
        initView()
    }

    private fun initExtra() {
        if (intent.hasExtra(EXTRA_DATA)) {
            searchData = intent.getParcelableExtra(EXTRA_DATA)
        }
    }

    private fun initView() {
        searchData?.let {
            binding.apply {
                searchItem = searchData
                btUrlMove.setOnClickListener {
                    Intent(this@DetailActivity, WebviewActivity::class.java).apply {
                        putExtra(WebviewActivity.EXTRA_URL, searchItem?.url)
                        putExtra(WebviewActivity.EXTRA_TITLE, searchItem?.title)
                        startActivity(this)
                    }
                }
                tbDetail.title = HtmlCompat.fromHtml(searchItem?.label ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
                setSupportActionBar(tbDetail)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
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