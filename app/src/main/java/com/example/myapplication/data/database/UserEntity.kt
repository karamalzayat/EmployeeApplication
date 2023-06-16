package com.example.myapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @ColumnInfo(name = "user_name")
    var userName: String,
    @ColumnInfo(name = "user_age")
    var userAge: String,
    @ColumnInfo(name = "user_jobTitle")
    var userJobTitle: String,
    @ColumnInfo(name = "user_Gender")
    var userGender: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}