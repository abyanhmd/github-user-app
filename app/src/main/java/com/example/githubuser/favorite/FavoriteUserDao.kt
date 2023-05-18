package com.example.githubuser.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addFavorite(favUser: FavoriteUser)

    @Query("SELECT * FROM fav_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT COUNT(*) FROM fav_user WHERE fav_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    suspend fun removeFavorite(id: Int): Int
}