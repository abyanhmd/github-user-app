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
import com.example.githubuser.network.FollowersResponse
import com.example.githubuser.network.RetrofitConfig
import com.example.githubuser.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowers: RecyclerView
    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        progressBar = view.findViewById(R.id.progressBarFollowers)
        rvFollowers = view.findViewById(R.id.rv_followers)

        displayFollowers(username.toString())
    }

    private fun displayFollowers(username: String) {
        showLoading(true)

        viewModel.getFollowersResponse(username)
        viewModel.followersResponse.observe(viewLifecycleOwner) { if(it != null) setFollowers(it) }
        showLoading(false)
    }

    private fun setFollowers(data: List<FollowersResponse>) {
        val listFollowers = ArrayList<User>()
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
            listFollowers.add(user)
        }
        rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        val adapter = UserFollowAdapter(listFollowers)
        rvFollowers.adapter = adapter
    }

    companion object {
        const val TAG = "FollowersFragment"
        const val USERNAME = "abyanhmd"
    }
}