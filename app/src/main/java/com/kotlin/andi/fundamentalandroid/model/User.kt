package com.kotlin.andi.fundamentalandroid.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var avatar: String? = null,
    var company: String? = null,
    var follower:  Int = 0,
    var following:  Int = 0,
    var location: String? = null,
    var name: String? = null,
    var repository:  Int = 0,
    var username: String? = null
): Parcelable