package com.kotlin.andi.consumerapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

import com.kotlin.andi.consumerapp.model.UserDBModel
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {

    fun getUserList(): LiveData<List<UserDBModel>> = liveData(Dispatchers.IO){
        emit(source.getUsers())
    }

}