package com.champnc.newsapp_scgtest.service

import com.champnc.newsapp_scgtest.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("https://newsapi.org/v2/everything?q=android&pageSize=20&apiKey=a64ced69f50843ffa61e65d68aa95d1f")
    fun getQuery(): Call<NewsResponse>

    @GET("https://newsapi.org/v2/everything?pageSize=20&apiKey=a64ced69f50843ffa61e65d68aa95d1f")
    fun getQuery(@Query("q") query: String): Call<NewsResponse>

    @GET("https://newsapi.org/v2/everything?pageSize=20&apiKey=a64ced69f50843ffa61e65d68aa95d1f")
    fun getQuery(@Query("q") query: String, @Query("page") page: Int): Call<NewsResponse>
}