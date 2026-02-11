package com.example.basicapp.data.remote

import com.example.basicapp.data.model.GithubUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GithubService {

    @GET("users")
    fun getUsers(): Call<List<GithubUser>>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/"

    //lazy bhaneko code runs only once, first time service is accessed->result cached->reused for future accesses
    val service: GithubService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GithubService::class.java)
    }
}
