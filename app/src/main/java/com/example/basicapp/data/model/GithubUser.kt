package com.example.basicapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class GithubUser(

    @PrimaryKey 
    val id: Int,

    val login: String,
    val avatar_url: String,
    val html_url: String,
    val type: String
)
