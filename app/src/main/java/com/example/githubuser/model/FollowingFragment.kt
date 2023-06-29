package com.example.githubuser.model

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.UserFollowAdapter
import com.example.githubuser.network.FollowingResponse
import com.example.githubuser.network.RetrofitConfig
import com.example.githubuser.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowing: RecyclerView
    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]
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

        viewModel.getFollowingResponse(username)
        viewModel.followingResponse.observe(viewLifecycleOwner) {
            if(it != null ) setFollowing(it)
        }
        showLoading(false)
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
        val adapter = UserFollowAdapter(listFollowing)
        rvFollowing.adapter = adapter
    }

    companion object {
        const val TAG = "FollowingFragment"
        const val USERNAME = "abyanhmd"
    }
}