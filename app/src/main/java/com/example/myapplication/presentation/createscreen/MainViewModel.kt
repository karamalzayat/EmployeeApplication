package com.example.myapplication.presentation.createscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.EmployeeDataBase
import com.example.myapplication.data.database.UserEntity
import com.example.myapplication.data.model.User
import com.example.myapplication.domain.repo.UserRepository
import com.example.myapplication.presentation.utils.Resources
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var mainFlow: MutableSharedFlow<Resources.CustomCommands> = MutableSharedFlow()
    private val userDao = EmployeeDataBase.getInstance(application).userDatabaseDao()
    private var userRepository: UserRepository = UserRepository(userDao)

    var userName = MutableLiveData("")
    var userAge = MutableLiveData("")
    var userJobTitle = MutableLiveData("")

    var selectedGender = ""
    fun createPressed() {
        viewModelScope.launch {
            if (userName.value!!.isNotEmpty() && userAge.value!!.isNotEmpty()  && userJobTitle.value!!.isNotEmpty()  && selectedGender.isNotEmpty() ) {
                try {
                    addNewUser(
                        User(
                            name = userName.value.toString(),
                            jobTitle = userJobTitle.value.toString(),
                            gender = selectedGender,
                            age = userAge.value.toString()
                        )
                    )
                    mainFlow.emit(Resources.CustomCommands.CreateNewUserPressed)
                } catch (e: Exception) {
                    mainFlow.emit(Resources.CustomCommands.CreateNewUserFailed)
                }
            } else {
                mainFlow.emit(Resources.CustomCommands.CreateNewUserFillRequirments)
            }

        }
    }

    private fun addNewUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }

    fun finish() {
        viewModelScope.launch {
            mainFlow.emit(Resources.CustomCommands.FinishListFragment)
        }
    }


    fun getAllUsers(): LiveData<List<UserEntity>> {
        return userRepository.getUsers()
    }

}