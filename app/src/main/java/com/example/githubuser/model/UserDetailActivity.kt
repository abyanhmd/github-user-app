package com.example.githubuser.model

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.TabPagerAdapter
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.network.UserDetailResponse
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityUserDetailBinding
    private lateinit var data: UserDetailResponse
    private lateinit var detailViewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        detailViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        val username = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(AVATAR_URL)

        displayDetail(username.toString())

        val tabPagerAdapter = TabPagerAdapter(this)
        tabPagerAdapter.username = username.toString()

        detailBinding.viewPager.adapter = tabPagerAdapter
        TabLayoutMediator(detailBinding.tabs, detailBinding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_NAME[position])
        }.attach()

        supportActionBar?.elevation = 0f

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        detailBinding.btnFavorite.isChecked = true
                        isChecked = true
                    } else {
                        detailBinding.btnFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        detailBinding.btnFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null && avatarUrl != null) {
                    detailViewModel.addFavorite(username, id, avatarUrl)
                }

            } else {
                detailViewModel.removeFavorite(id)
            }
            detailBinding.btnFavorite.isChecked = isChecked
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            detailBinding.progressBar.visibility = View.VISIBLE
        } else {
            detailBinding.progressBar.visibility = View.GONE
        }
    }

    private fun displayDetail(username: String) {
        showLoading(true)

        detailViewModel.getDetailResponse(username)
        detailViewModel.detailUser.observe(this) { if (it != null) setUser(it) }
        showLoading(false)
    }

    private fun setUser(data: UserDetailResponse) {
        Glide.with(this)
            .load(data.avatarUrl)
            .circleCrop()
            .into(detailBinding.imgAvatar)

        val notFound = "-"
        if (data.name != null) detailBinding.tvName.text = data.name
        else detailBinding.tvName.text = notFound

        detailBinding.tvName.text = data.name
        detailBinding.tvUsername.text = "@${data.login}"
        detailBinding.tvRepository.text = data.publicRepos.toString()

        if (data.location == null) detailBinding.tvLocation.text = data.location
        else detailBinding.tvLocation.text = notFound

        if (data.company != null) detailBinding.tvCompany.text = data.company
        else detailBinding.tvCompany.text = notFound
    }


    companion object {
        const val TAG = "UserDetailActivity"
        const val USERNAME = "username"
        const val ID = "id"
        const val AVATAR_URL = "avatar_url"

        private val TAB_NAME = intArrayOf(R.string.tab_left, R.string.tab_right)
    }
}