package com.kotlin.andi.fundamentalandroid.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val avatar: Int,
    val company: String,
    val follower: String,
    val following: String,
    val location: String,
    val name: String,
    val repository: String,
    val username: String
): Parcelable