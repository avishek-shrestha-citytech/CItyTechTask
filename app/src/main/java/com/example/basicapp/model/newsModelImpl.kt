package com.example.basicapp.model

import android.util.Log
import com.example.basicapp.data.apiKeys
import com.example.basicapp.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsModelImpl {
    fun fetchNews() {
        val apiService = RetrofitClient.instance

        apiService.getTopHeadlines("technology", apiKeys.GNEWS_API_KEY)
            .enqueue(object : Callback<NewsApiResponse> {
                override fun onResponse(call: Call<NewsApiResponse>, response: Response<NewsApiResponse>) {
                    if (response.isSuccessful) {
                        val newsList = response.body()?.articles

                    }
                }

                override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                    Log.e("API_ERROR", t.message.toString())
                }
            })
    }
}