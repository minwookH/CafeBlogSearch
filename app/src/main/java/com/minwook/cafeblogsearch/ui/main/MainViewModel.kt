package com.minwook.cafeblogsearch.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minwook.cafeblogsearch.data.*
import com.minwook.cafeblogsearch.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchList = MutableLiveData<ArrayList<SearchItem>>()
    val searchList: LiveData<ArrayList<SearchItem>>
        get() = _searchList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val compositeDisposable = CompositeDisposable()

    fun loadSearchList(text: String, page: Int = 1) {
        Log.e("search", "loadSearchList text start : $text")
        // todo merge

        /*compositeDisposable.add(
            searchRepository.getBlogSearchResult(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _searchList.value = it.documents


                    Log.e("coin", "loadSearchList blog # : ${it.documents}")
                }, {
                    Log.e("coin", "loadSearchList error : ${it.localizedMessage}")
                })
        )

        compositeDisposable.add(
            searchRepository.getCafeSearchResult(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //_searchList.value = it.documents

                    Log.e("coin", "loadSearchList cafe ~ : ${it.documents}")
                }, {
                    Log.e("coin", "loadSearchList error : ${it.localizedMessage}")
                })
        )*/

        compositeDisposable.add(
            Single.zip(
                searchRepository.getBlogSearchResult(text, page).subscribeOn(Schedulers.io()),
                searchRepository.getCafeSearchResult(text, page).subscribeOn(Schedulers.io()),
                BiFunction { firstResonse: BlogResponse,
                             secondResponse: CafeResponse ->
                    addItemList(firstResonse.documents, secondResponse.documents)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("search", "loadSearchList zip ~11 : $it")
                    it.sortByDescending { item -> item.title }
                    Log.d("search", "loadSearchList zip ~22 : $it")
                    _searchList.value = it
                }, {
                    Log.e("search", "loadSearchList error : ${it.localizedMessage}")
                })
        )
    }

    private fun addItemList(firstList: ArrayList<Blog>, secondList: ArrayList<Cafe>): ArrayList<SearchItem> {
        val searchList = arrayListOf<SearchItem>()

        firstList.forEach {
            searchList.add(SearchItem(it.title, it.contents, it.url, it.blogname, it.thumbnail, it.datetime, "blog"))
        }

        secondList.forEach {
            searchList.add(SearchItem(it.title, it.contents, it.url, it.cafename, it.thumbnail, it.datetime, "cafe"))
        }

        Log.d("search", "zip Blog size : ${firstList.size} , Cafe size : ${secondList.size} , searchList : ${searchList.size}")
        return searchList
    }

    fun disposableAll() {
        compositeDisposable.dispose()
    }
}