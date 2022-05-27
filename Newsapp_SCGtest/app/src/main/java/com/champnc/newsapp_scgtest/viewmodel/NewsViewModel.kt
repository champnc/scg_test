package com.champnc.newsapp_scgtest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.champnc.newsapp_scgtest.model.Article
import com.champnc.newsapp_scgtest.model.NewsResponse
import com.champnc.newsapp_scgtest.service.NewsReteofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel(){
    private val _newResponse: MutableLiveData<NewsResponse> by lazy {
        MutableLiveData<NewsResponse>().also {
            loadNews()
        }
    }

    val users : LiveData<NewsResponse> = _newResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var pageIndex = 1

    private fun loadNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val call = NewsReteofit.newsService.getQuery()
            call.enqueue(object: Callback<NewsResponse>{
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    _isLoading.postValue(false)
                    if (response.isSuccessful) {
                        _newResponse.value = response.body()
                    } else {

                    }

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                    Log.d("debug","no data")
                }
            })
        }

    }

    fun search(query: String?) {
        if (!query.isNullOrEmpty()) {
            pageIndex = 0
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                val call = NewsReteofit.newsService.getQuery(query)
                call.enqueue(object: Callback<NewsResponse>{
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        _isLoading.postValue(false)
                        if (response.isSuccessful) {
                            _newResponse.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        _isLoading.postValue(false)
                        Log.d("debug","no data")
                    }
                })
            }
        }
    }

    fun searchNext(query: String?) {
        if (!query.isNullOrEmpty()) {
            pageIndex++
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                val call = NewsReteofit.newsService.getQuery(query,pageIndex)
                call.enqueue(object: Callback<NewsResponse>{
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        _isLoading.postValue(false)
                        if (response.isSuccessful){
                            if (_newResponse.value == null) {
                                _newResponse.value = response.body()
                            } else {
                                val oldResponse = _newResponse.value
                                var oldArticle = oldResponse?.articles as MutableList<Article>
                                var newArticle = response.body()?.articles
                                if (newArticle != null) {
                                    oldArticle.addAll(newArticle)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        _isLoading.postValue(false)
                        Log.d("debug","no data")
                    }
                })
            }
        }
    }
}