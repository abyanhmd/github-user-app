package com.example.githubuser.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var favUserDao: FavoriteUserDao? = userDatabase?.favoriteUserDao()

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return favUserDao?.getFavoriteUser()
    }
}