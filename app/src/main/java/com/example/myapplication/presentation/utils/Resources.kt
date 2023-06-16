package com.example.myapplication.presentation.utils

sealed class Resources() {
    sealed class CustomCommands(
        var message: String = "",
    ) {

        object CreateNewUserPressed : CustomCommands()
        object CreateNewUserFillRequirments : CustomCommands()
        object CreateNewUserFailed : CustomCommands()
        object FinishListFragment : CustomCommands()
    }
}