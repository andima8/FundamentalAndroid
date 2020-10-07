package com.kotlin.andi.fundamentalandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.andi.fundamentalandroid.BuildConfig
import com.kotlin.andi.fundamentalandroid.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    val usersDetail = MutableLiveData<User>()

    fun setDetailUsers(username: String?){
        val client =  AsyncHttpClient()
        client.addHeader("Authorization","token " + BuildConfig.GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")
        val url = " https://api.github.com/users/${username}"
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonObject = JSONObject(result)
                    val userDetail = User()
                    userDetail.username = jsonObject.getString("login")
                    userDetail.name = jsonObject.getString("name")
                    userDetail.avatar = jsonObject.getString("avatar_url")
                    userDetail.company = jsonObject.getString("company")
                    userDetail.location = jsonObject.getString("location")
                    userDetail.repository = jsonObject.getString("public_repos")
                    userDetail.gists = jsonObject.getString("public_gists")
                    userDetail.follower = jsonObject.getString("followers")
                    userDetail.followersUrl = jsonObject.getString("followers_url")
                    userDetail.following = jsonObject.getString("following")
                    userDetail.followingUrl = jsonObject.getString("following_url")

                    usersDetail.postValue(userDetail)
                } catch (e: Exception) {
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

    fun getDetailUsers(): LiveData<User> {
        return usersDetail
    }

}