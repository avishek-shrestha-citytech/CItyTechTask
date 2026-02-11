package com.example.basicapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basicapp.data.model.GithubUser

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<GithubUser>>

    //onConflict.REPLACE bhaneko same ID ko user pailai xa bhane paile ko entry lai delete garne and new entry lai insert garne
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<GithubUser>)

    @Query("DELETE FROM users")
    fun deleteAll()
}
