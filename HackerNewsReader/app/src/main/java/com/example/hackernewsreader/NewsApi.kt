package com.example.hackernewsreader

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
    @GET("topstories.json")
    fun getNewsList(): Call<IntArray>

    @GET("item/{newsId}.json")
    fun getNewsDetail(@Path("newsId") newsId: Int): Call<News>
}