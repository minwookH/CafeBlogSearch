package com.minwook.cafeblogsearch.ui.main

import com.minwook.cafeblogsearch.data.Header
import com.minwook.cafeblogsearch.data.SearchItem
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchListAdapterTest : TestCase() {

    private lateinit var adapter: SearchListAdapter

    @Before
    public override fun setUp() {
        adapter = SearchListAdapter()
    }

    @Test
    fun testClear() {
        adapter.addHeader(Header(0, 0, 0, 0))
        assertEquals(1, adapter.getList().size)

        adapter.clear()
        assertEquals(1, adapter.getList().size)
    }

    @Test
    fun testAddItem() {
        adapter.addHeader(Header(0, 0, 0, 0))
        adapter.addSearchItem(SearchItem("", "", "", "", "", "", ""))
        adapter.addSearchItem(SearchItem("", "", "", "", "", "", ""))

        assertEquals(3, adapter.getList().size)
        assertEquals(2, adapter.getSearchItemList().size)
    }
}
