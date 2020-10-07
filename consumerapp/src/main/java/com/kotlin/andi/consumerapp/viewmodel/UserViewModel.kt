package com.kotlin.andi.consumerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlin.andi.consumerapp.data.UserDataSource
import com.kotlin.andi.consumerapp.data.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val source =
            UserDataSource(application.contentResolver)
        repository =
            UserRepository(source)
    }
    var userLists = repository.getUserList()
}