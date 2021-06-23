package com.minwook.cafeblogsearch

import android.content.Intent
import android.os.Bundle
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
    private var page = 1
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserve()

        mainViewModel.loadSearchList("사과")
    }

    private fun initView() {
        searchListAdapter = SearchListAdapter().apply {
            onClickFillter = { fillter ->
                page = 1
                searchListAdapter.clear()
                searchListAdapter.searchListClear()
                fillterType = fillter

                when (fillter) {
                    "Blog" -> {
                        mainViewModel.loadBlogSearchList("사과")
                    }
                    "Cafe" -> {
                        mainViewModel.loadCafeSearchList("사과")
                    }
                    else -> {
                        mainViewModel.loadSearchList("사과")
                    }
                }
            }

            onClickSort = { sort ->
                val list = when (sort) {
                    "Title" -> {
                        searchListAdapter.getSearchItemList().sortByDescending { it.title }
                        searchListAdapter.getSearchItemList()
                    }
                    else -> {
                        searchListAdapter.getSearchItemList().sortByDescending { DateTime(it.datetime).millis }
                        searchListAdapter.getSearchItemList()
                    }
                }

                searchListAdapter.clear()
                searchListAdapter.sortRefresh(list)
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
                        when (fillterType) {
                            "Blog" -> {
                                mainViewModel.loadBlogSearchList("사과", ++page)
                            }
                            "Cafe" -> {
                                mainViewModel.loadCafeSearchList("사과", ++page)
                            }
                            else -> {
                                mainViewModel.loadSearchList("사과", ++page)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun initObserve() {
        mainViewModel.searchList.observe(this, {
            searchListAdapter.setSearchList(it)
        })
    }

    override fun onDestroy() {
        mainViewModel.disposableAll()

        super.onDestroy()
    }
}