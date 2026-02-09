package com.example.basicapp.remote

import android.util.Log
import com.example.basicapp.data.apiKeys
import com.example.basicapp.model.Article
import com.example.basicapp.model.NewsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface NewsCallback {
    fun onNewsLoaded(articles: List<Article>)
    fun onError(error: String)
}

class NewsModelImpl {
    fun fetchNews(callback: NewsCallback) {
        val apiService = RetrofitClient.instance

        apiService.getTopHeadlines("technology", apiKeys.GNEWS_API_KEY)
            .enqueue(object : Callback<NewsApiResponse> {
                override fun onResponse(call: Call<NewsApiResponse>, response: Response<NewsApiResponse>) {
                    if (response.isSuccessful) {
                        val newsList = response.body()?.articles ?: emptyList()
                        callback.onNewsLoaded(newsList)
                    } else {
                        callback.onError("Failed to load news: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                    Log.e("API_ERROR", t.message.toString())
                    callback.onError(t.message ?: "Unknown error")
                }
            })
    }
}