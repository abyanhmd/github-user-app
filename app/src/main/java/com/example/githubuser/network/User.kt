package com.example.githubuser.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String? = null,
    var avatarUrl: String? = null,
    var name: String? = null,
    var id: Int,
    var location: String? = null,
    var company: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var repository: String? = null
) : Parcelable
