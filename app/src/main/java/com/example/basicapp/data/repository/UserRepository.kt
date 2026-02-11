package com.example.basicapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.basicapp.data.local.UserDao
import com.example.basicapp.data.model.GithubUser
import com.example.basicapp.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val userDao: UserDao) {

    val users: LiveData<List<GithubUser>> = userDao.getAllUsers()

    fun refreshUsers() {
        RetrofitClient.service.getUsers().enqueue(object : Callback<List<GithubUser>> {

            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                if (response.isSuccessful) {
                    val userList = response.body()

                    if (userList != null) {
                        Thread {
                            userDao.deleteAll()
                            userDao.insertAll(userList)
                        }.start()
                    }
                } else {
                    Log.e("UserRepository", "API Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                Log.e("UserRepository", "Network Error: ${t.message}")
            }
        })
    }
}
