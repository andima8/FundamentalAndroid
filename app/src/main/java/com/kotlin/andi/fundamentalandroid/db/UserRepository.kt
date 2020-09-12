package com.kotlin.andi.fundamentalandroid.db

import androidx.lifecycle.LiveData

class UserRepository(private  val userDao: UserDao){

    val readAllUser: LiveData<List<UserDBModel>> = userDao.readAllUser()

    suspend fun addUser(userDBModel: UserDBModel){
        userDao.addUser(userDBModel)
    }

}