package com.kotlin.andi.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserDBModel(
    var id: Int = 0,
    var avatar: String? = null,
    var company: String? = null,
    var follower: String? = null,
    var following:  String? = null,
    var location: String? = null,
    var name: String? = null,
    var repository:  String? = null,
    var gists: String? = null,
    var username: String? = null
): Parcelable