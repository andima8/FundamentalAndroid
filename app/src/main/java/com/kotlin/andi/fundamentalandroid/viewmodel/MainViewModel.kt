package com.kotlin.andi.fundamentalandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.andi.fundamentalandroid.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel: ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()
    val listUser = ArrayList<User>()
    val usersDetail = MutableLiveData<User>()

    fun setUsers(username: String?){
        val client =  AsyncHttpClient()
        client.addHeader("Authorization","token 6421bccce2b34308de4edbf32fa0832287bb926a")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=${username}"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
              try {
                  listUser.clear()
                  val result = String(responseBody)
                  val responseObject = JSONObject(result)
                  val list = responseObject.getJSONArray("items")
                  Log.d("Hasil", "${result}")
                  for(i in 0 until list.length()){
                      val user = list.getJSONObject(i)
                      val userItems = User()
                      userItems.id = user.getInt("id")
                      userItems.avatar = user.getString("avatar_url")
                      userItems.username = user.getString("login")
                      listUser.add(userItems)
                  }
                  //set data ke adapter
                  Log.d("Total", "${listUser.size}")
                  listUsers.postValue(listUser)
              }  catch (e: Exception){

                  Log.d("Exception", e.message.toString())
              }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("Exception", error.message.toString())
            }

        })

    }
    fun getUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }


    fun setDetailUsers(username: String?){
        val client =  AsyncHttpClient()
        client.addHeader("Authorization","token 6421bccce2b34308de4edbf32fa0832287bb926a")
        client.addHeader("User-Agent", "request")
        val url = " https://api.github.com/users/${username}"
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val jsonObject = JSONObject(result)
                    val userDetail = User()
                    userDetail.username = jsonObject.getString("login")
                    userDetail.name = jsonObject.getString("name")
                    userDetail.avatar = jsonObject.getString("avatar_url")
                    userDetail.company = jsonObject.getString("company")
                    userDetail.location = jsonObject.getString("location")
                    userDetail.repository = jsonObject.getString("public_repos")
                    userDetail.follower = jsonObject.getString("followers")
                    userDetail.followersUrl = jsonObject.getString("followers_url")
                    userDetail.following = jsonObject.getString("following")
                    userDetail.followingUrl = jsonObject.getString("following_url")
                   // listDetailUser.add(userDetail)
                    usersDetail.postValue(userDetail)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Exception", error?.message.toString())
            }

        })
    }

    fun getDetailUsers(): LiveData<User>{
        return usersDetail
    }

}