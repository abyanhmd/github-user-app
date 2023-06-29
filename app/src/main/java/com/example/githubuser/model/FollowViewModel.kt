package com.example.githubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.network.FollowingResponse
import com.example.githubuser.network.RetrofitConfig
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class FollowViewModel: ViewModel() {

    private val _followingResponse = MutableLiveData<List<FollowingResponse>?>()
    val followingResponse : LiveData<List<FollowingResponse>?> = _followingResponse

    fun getFollowingResponse(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitConfig.getUserService().getUserFollowing(username).awaitResponse()

                if(response.isSuccessful) {
                    val body = response.body()

                    if(body != null) _followingResponse.value = body

                    Log.d("FollowViewModel", "onResponse: ${body.toString()}")
                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            } catch (t: Throwable) {
                Log.e("FollowViewModel", "onFailure: ${t.message}")
            }
        }
    }
}