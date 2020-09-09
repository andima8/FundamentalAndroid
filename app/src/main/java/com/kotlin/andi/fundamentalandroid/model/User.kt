package com.kotlin.andi.fundamentalandroid.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var avatar: String? = null,
    var company: String? = null,
    var follower:  String? = null,
    var followersUrl: String? = null,
    var following:  String? = null,
    var followingUrl:  String? = null,
    var location: String? = null,
    var name: String? = null,
    var repository:  String? = null,
    var username: String? = null
): Parcelable