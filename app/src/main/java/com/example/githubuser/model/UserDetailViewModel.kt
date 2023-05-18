package com.example.githubuser.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubuser.favorite.FavoriteUser
import com.example.githubuser.favorite.FavoriteUserDao
import com.example.githubuser.favorite.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var favUserDao: FavoriteUserDao? = userDatabase?.favoriteUserDao()

    fun addFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(username, id, avatarUrl)
            favUserDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favUserDao?.checkUser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favUserDao?.removeFavorite(id)
        }
    }
}