package com.example.githubuser.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.adapter.ListUserAdapter.OnItemClickCallback
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.model.UserDetailActivity
import com.example.githubuser.network.User

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favBinding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favBinding = ActivityFavoriteBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(favBinding.root)

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        favBinding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                listMap(it)
            }
        }
    }

    private fun listMap(user: List<FavoriteUser>) {
        val listUsers = ArrayList<User>()
        for (i in user) {
            val mapUser = User(
                i.username,
                i.avatarUrl,
                null,
                i.id
            )
            listUsers.add(mapUser)
        }
        favBinding.rvFavorite.adapter = ListUserAdapter(listUsers)
    }
}