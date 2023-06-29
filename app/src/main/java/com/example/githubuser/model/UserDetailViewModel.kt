package com.example.githubuser.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.favorite.FavoriteUser
import com.example.githubuser.favorite.FavoriteUserDao
import com.example.githubuser.favorite.UserDatabase
import com.example.githubuser.network.RetrofitConfig
import com.example.githubuser.network.UserDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

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

    private val _detailUser = MutableLiveData<UserDetailResponse?>()
    val detailUser : LiveData<UserDetailResponse?> = _detailUser

    fun getDetailResponse(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitConfig.getUserService().getUserDetail(username).awaitResponse()

                if(response.isSuccessful) {
                    val body = response.body()

                    if(body != null) _detailUser.value = body

                    Log.d("UserDetailViewModel", "onResponse: ${body.toString()}")
                } else {
                    Log.e("UserDetailViewModel", "onFailure: ${response.message()}")
                }
            } catch (t: Throwable) {
                Log.e("UserDetailViewModel", "onFailure: ${t.message}")
            }
        }
    }
}