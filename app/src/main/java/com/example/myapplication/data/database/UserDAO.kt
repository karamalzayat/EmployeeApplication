package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    @Query("SELECT * FROM UserEntity")
    fun getUserDetails(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(vararg user: UserEntity)

}