package com.example.githubuser.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    companion object {
        const val key = "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"
    }

    @GET("search/users")
    @Headers("Authorization: token $key")
    fun getUsers(
        @Query("q") q: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $key")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $key")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<FollowersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $key")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponse>>
}