package com.example.basicapp.remote

import com.example.basicapp.model.NewsApiResponse
import com.example.basicapp.model.newsModelImpl
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GNewsService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String,
        @Query("apikey") apiKey: String,
        @Query("lang") language: String = "en"
    ): Call<NewsApiResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://gnews.io/api/v4/"

    val instance: GNewsService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GNewsService::class.java)
    }
}
