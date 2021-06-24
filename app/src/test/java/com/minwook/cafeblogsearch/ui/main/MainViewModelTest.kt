package com.minwook.cafeblogsearch.ui.main

import com.minwook.cafeblogsearch.data.Blog
import com.minwook.cafeblogsearch.data.Cafe
import com.minwook.cafeblogsearch.db.SearchDAO
import com.minwook.cafeblogsearch.network.ServerAPI
import com.minwook.cafeblogsearch.repository.SearchRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest : TestCase() {

    val serverAPI = Mockito.mock(ServerAPI::class.java)
    val searchDAO = Mockito.mock(SearchDAO::class.java)
    val searchRepository = SearchRepository(serverAPI, searchDAO)
    private lateinit var viewModel: MainViewModel

    @Before
    public override fun setUp() {
        viewModel = MainViewModel(searchRepository)
    }

    @Test
    fun testCombineItemList() {
        val combineItemList = viewModel.combineItemList(
            arrayListOf(Blog("", "", "", "", "", "")),
            arrayListOf(Cafe("", "", "", "", "", ""))
        )

        assertEquals(2, combineItemList.size)
    }
}
