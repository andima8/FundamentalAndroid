package com.kotlin.andi.fundamentalandroid.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "user_table")
@Parcelize
data class UserDBModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var avatar: String,
    var company: String,
    var follower:  String,
    var following:  String,
    var location: String,
    var name: String,
    var repository:  String,
    var gists: String,
    var username: String
): Parcelable