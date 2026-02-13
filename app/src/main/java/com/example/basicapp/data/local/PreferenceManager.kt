package com.example.basicapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_SORT_OPTION = "sort_option"
        private const val DEFAULT_SORT = "Sort by ID"
    }

    fun saveSortOption(option: String) {
        prefs.edit {
            putString(KEY_SORT_OPTION, option)
        }
    }

    fun getSortOption(): String {
        return prefs.getString(KEY_SORT_OPTION, DEFAULT_SORT) ?: DEFAULT_SORT
    }

    fun clearAll() {
        prefs.edit {
            clear()
        }
    }
}
