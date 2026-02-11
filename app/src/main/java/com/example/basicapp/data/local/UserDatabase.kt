package com.example.basicapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.basicapp.data.model.GithubUser

@Database(entities = [GithubUser::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    //companion object bhanya java ko static jastai, same for all instances of class
    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                .fallbackToDestructiveMigration(false)
                .build()
                
                // Store the instance for future use
                INSTANCE = instance
                
                // Return the instance
                instance
            }
        }
    }
}
