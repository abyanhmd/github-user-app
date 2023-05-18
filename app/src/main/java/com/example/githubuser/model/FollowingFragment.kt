package com.example.githubuser.model

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.FollowanceAdapter
import com.example.githubuser.network.FollowingResponse
import com.example.githubuser.network.RetrofitConfig
import com.example.githubuser.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowing: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        progressBar = view.findViewById(R.id.progressBarFollowing)
        rvFollowing = view.findViewById(R.id.rv_following)

        displayFollowing(username.toString())
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun displayFollowing(username: String) {
        showLoading(true)
        Log.d(TAG, "displayFollowing: ")

        val client = RetrofitConfig.getUserService().getUserFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponse>> {
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) setFollowing(responseBody)

                    Log.d(TAG, "onResponse: ${responseBody.toString()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setFollowing(data: List<FollowingResponse>) {
        val listFollowing = ArrayList<User>()
        for (item in data) {
            val user = User(
                item.login,
                item.avatarUrl,
                null,
                item.id,
                null,
                null,
                null,
                null
            )
            listFollowing.add(user)
        }
        rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FollowanceAdapter(listFollowing)
        rvFollowing.adapter = adapter
    }

    companion object {
        const val TAG = "FollowingFragment"
        const val USERNAME = "abyanhmd"
    }
}