package com.example.githubuser

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.model.DarkModePreferences
import com.example.githubuser.network.RetrofitConfig
import com.example.githubuser.network.UserResponse
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MainViewModel(private val pref: DarkModePreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }

    private val _userInfo = MutableLiveData<UserResponse?>()
    val userInfo: LiveData<UserResponse?> = _userInfo

    fun getUserResponse(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitConfig.getUserService().getUsers(username).awaitResponse()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) _userInfo.value = body

                    Log.d("MainViewModel", "onResponse: ${body.toString()}")
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            } catch (t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }


        }
    }
}