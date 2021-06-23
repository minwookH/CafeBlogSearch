package com.minwook.cafeblogsearch

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.minwook.cafeblogsearch.data.Header
import com.minwook.cafeblogsearch.databinding.ActivityMainBinding
import com.minwook.cafeblogsearch.ui.main.MainViewModel
import com.minwook.cafeblogsearch.ui.main.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private val mainViewModel by viewModels<MainViewModel>()

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserve()

        mainViewModel.loadSearchList("사과")
    }

    private fun initView() {
        searchListAdapter = SearchListAdapter()
        searchListAdapter.addHeader(Header(R.array.fillter_array))

        binding.apply {
            rvList.adapter = searchListAdapter
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initObserve() {
        mainViewModel.searchList.observe(this, {
            Log.d("search", "initObserve size : ${it.size}")
            searchListAdapter.setSearchList(it)
        })
    }

    override fun onDestroy() {
        mainViewModel.disposableAll()

        super.onDestroy()
    }
}