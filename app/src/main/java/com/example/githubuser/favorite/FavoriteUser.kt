package com.example.githubuser.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fav_user")
data class FavoriteUser(
    val username: String,

    @PrimaryKey
    val id: Int,

    val avatarUrl: String
) : Serializable
