package com.champnc.newsapp_scgtest.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsReteofit {
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/")
            .build()
    }

    val newsService : NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }
}