package com.kotlin.andi.fundamentalandroid.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
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
)