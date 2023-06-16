package com.example.myapplication.domain.repo

import androidx.lifecycle.LiveData
import com.example.myapplication.data.database.UserDAO
import com.example.myapplication.data.database.UserEntity
import com.example.myapplication.data.model.User


class UserRepository(private val userDAO: UserDAO) {
    suspend fun addUser(user: User) {
        userDAO.insertUser(
            UserEntity(
                userGender = user.gender,
                userAge = user.age,
                userJobTitle = user.jobTitle,
                userName = user.name
            )
        )
    }

    fun getUsers(): LiveData<List<UserEntity>> {
        return userDAO.getUserDetails()
    }
}