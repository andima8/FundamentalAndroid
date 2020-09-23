package com.kotlin.andi.fundamentalandroid.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllUser: LiveData<List<UserDBModel>> //remove private if using it in Favorite Class
    val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllUser = repository.readAllUser
    }

    fun checkUser(username: String): LiveData<List<UserDBModel>> = repository.checkUser(username)

    fun addUser(userDBModel: UserDBModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userDBModel)
        }
    }

    fun deleteUser(userDBModel: UserDBModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userDBModel)
        }
    }

}