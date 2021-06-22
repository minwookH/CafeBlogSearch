package com.minwook.cafeblogsearch.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minwook.cafeblogsearch.data.BlogResponse
import com.minwook.cafeblogsearch.data.CafeResponse
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

    private val _searchList = MutableLiveData<List<Any>>()
    val searchList: LiveData<List<Any>>
        get() = _searchList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val compositeDisposable = CompositeDisposable()

    fun loadSearchList(text: String) {
        Log.e("coin", "loadSearchList text start : $text")
        // todo merge

        compositeDisposable.add(
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
        )

        compositeDisposable.add(
            Single.zip(
                searchRepository.getBlogSearchResult(text).subscribeOn(Schedulers.io()),
                searchRepository.getCafeSearchResult(text).subscribeOn(Schedulers.io()),
                BiFunction { firstResonse: BlogResponse,
                             secondResponse: CafeResponse ->
                    {
                        val searchList = arrayListOf<Any>()
                        searchList.addAll(firstResonse.documents)
                        searchList.addAll(secondResponse.documents)
                        Log.e("coin", "loadSearchList Blog size : ${firstResonse.documents.size} , Cafe size : ${secondResponse.documents.size}")
                        searchList
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("coin", "loadSearchList cafe ~ : ${listOf(it).size}")
                    _searchList.value = listOf(it)
                }, {
                    Log.e("coin", "loadCoinTickerList error : ${it.localizedMessage}")
                })
        )
    }

    fun disposableAll() {
        compositeDisposable.dispose()
    }
}