package com.example.basicapp.model

import androidx.annotation.DrawableRes

data class SettingItem(
    val id: Int,
    val title: String,
    @DrawableRes val iconResId: Int
)