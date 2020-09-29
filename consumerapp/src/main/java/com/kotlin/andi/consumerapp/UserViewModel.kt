package com.kotlin.andi.consumerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val source = UserDataSource(application.contentResolver)
        repository = UserRepository(source)
    }
    var userLists = repository.getUserList()
}