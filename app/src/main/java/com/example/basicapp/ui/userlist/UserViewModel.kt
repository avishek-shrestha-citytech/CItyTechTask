package com.example.basicapp.ui.userlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.basicapp.data.local.UserDatabase
import com.example.basicapp.data.model.GithubUser
import com.example.basicapp.data.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val users: LiveData<List<GithubUser>>

    private val _isLoading = androidx.lifecycle.MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        val database = UserDatabase.getInstance(application)
        val userDao = database.userDao()
        repository = UserRepository(userDao)
        users = repository.users
        refreshUsers()
    }

    fun refreshUsers() {
        repository.refreshUsers { loading ->
            _isLoading.postValue(loading)
        }
    }
}
