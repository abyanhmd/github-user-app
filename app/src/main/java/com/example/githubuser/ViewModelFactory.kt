package com.example.githubuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubuser.model.DarkModePreferences

class ViewModelFactory(private val pref: DarkModePreferences) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(pref) as T

        throw IllegalArgumentException("Unknown ViewModel : " + modelClass.name)
    }
}