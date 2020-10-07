package com.kotlin.andi.fundamentalandroid.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "user_table")
@Parcelize
data class UserDBModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var avatar: String? = null,
    var company: String? = null,
    var follower:  String? = null,
    var following:  String? = null,
    var location: String? = null,
    var name: String? = null,
    var repository:  String? = null,
    var gists: String? = null,
    var username: String? = null
): Parcelable