package com.minwook.cafeblogsearch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.data.Header
import com.minwook.cafeblogsearch.databinding.ActivityMainBinding
import com.minwook.cafeblogsearch.ui.detail.DetailActivity
import com.minwook.cafeblogsearch.ui.main.MainViewModel
import com.minwook.cafeblogsearch.ui.main.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var fillterType: String
    private lateinit var searchHistoryAdapter: ArrayAdapter<String>
    private val mainViewModel by viewModels<MainViewModel>()
    private var page = 1
    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserve()
        initRxBus()
        mainViewModel.getSearchHistory()
    }

    private fun initView() {
        searchListAdapter = SearchListAdapter().apply {
            onClickFillter = { fillter ->
                searchInit()
                fillterType = fillter
                mainViewModel.getSearch(searchText, fillter)
            }

            onClickSort = { sort ->
                when (sort) {
                    "Title" -> {
                        searchListAdapter.getSearchItemList().sortBy { it.title }
                        searchListAdapter.getSearchItemList()
                    }
                    else -> {
                        searchListAdapter.getSearchItemList().sortByDescending { DateTime(it.datetime).millis }
                        searchListAdapter.getSearchItemList()
                    }
                }

                searchListAdapter.sortRefresh()
            }

            onClickItem = { data ->
                Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_DATA, data)
                    startActivity(this)
                }
            }
        }

        searchListAdapter.addHeader(Header(R.array.fillter_array, R.array.sort_array, 0, 0))
        fillterType = resources.getStringArray(R.array.fillter_array)[0]

        binding.apply {
            rvList.adapter = searchListAdapter
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter?.itemCount ?: 0

                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition >= itemTotalCount - 1) {
                        mainViewModel.getSearch(searchText, fillterType, ++page)
                    }
                }
            })

            actvSearch.setOnItemClickListener { parent, view, position, id ->
                keyboardHide()
                searchHistoryAdapter.getItem(position)?.let {
                    searchText = it
                    searchInit()
                    mainViewModel.getSearch(it, fillterType)
                }
            }

            btSearch.setOnClickListener {
                keyboardHide()
                searchText = actvSearch.text.toString()
                searchHistoryAdapter.add(actvSearch.text.toString())
                mainViewModel.insertSearchHistory(actvSearch.text.toString())
                searchInit()
                mainViewModel.getSearch(searchText, fillterType)
            }
        }
    }

    private fun initObserve() {
        mainViewModel.searchList.observe(
            this,
            {
                searchListAdapter.setSearchList(it)
            }
        )

        mainViewModel.searchHistoryList.observe(
            this,
            {
                searchHistoryAdapter =
                    ArrayAdapter<String>(this@MainActivity, android.R.layout.select_dialog_item, it)
                binding.actvSearch.setAdapter(searchHistoryAdapter)
            }
        )
    }

    @SuppressLint("CheckResult")
    private fun initRxBus() {
        RxBus.listen(String::class.java).subscribe { url ->
            val position = searchListAdapter.getSearchItemList().indexOfFirst { it.url == url }
            searchListAdapter.setWebPageCheck(position + 1)
        }
    }

    private fun searchInit() {
        page = 1
        searchListAdapter.clear()
        searchListAdapter.searchListClear()
    }

    private fun keyboardHide() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
