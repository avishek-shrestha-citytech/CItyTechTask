package com.example.basicapp.ui.userlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import com.example.basicapp.data.local.PreferenceManager
import com.example.basicapp.data.local.UserDatabase
import com.example.basicapp.data.model.GithubUser
import com.example.basicapp.data.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    //Shared Preferences
    private val preferenceManager = PreferenceManager(application)


    private val _users = MediatorLiveData<List<GithubUser>>()
    val users: LiveData<List<GithubUser>> get() = _users

    private val _sortOption = MutableLiveData<String>()

    private val repository: UserRepository
    private var rawUsers: List<GithubUser> = emptyList()

    fun setSortOption(option: String) {
        _sortOption.value = option
        _users.value = sortUsers(rawUsers, option)

        //save in the shared preferences
        preferenceManager.saveSortOption(option)
    }

    //get saved setting from the preference manager
    fun getSavedSortOption(): String {
        return preferenceManager.getSortOption()
    }

    private fun sortUsers(list: List<GithubUser>, option: String?): List<GithubUser> {
        return when (option) {
            "Sort by Name" -> list.sortedBy { it.login.lowercase() }
            "Sort by ID" -> list.sortedBy { it.id }
            else -> list
        }
    }

    //Spinning Loader
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        val database = UserDatabase.getInstance(application)
        val userDao = database.userDao()
        repository = UserRepository(userDao)

        // get the sort option from the shared preferences
        _sortOption.value = preferenceManager.getSortOption()

        _users.addSource(repository.users) { list ->
            rawUsers = list
            // Always apply current sort option to the new data
            _users.value = sortUsers(rawUsers, _sortOption.value)
        }

        refreshUsers()
    }

    fun refreshUsers() {
        repository.refreshUsers { loading ->
            _isLoading.postValue(loading)
        }
    }
}
