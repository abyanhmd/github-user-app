package com.example.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.model.FollowersFragment
import com.example.githubuser.model.FollowingFragment

class TabPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowersFragment.USERNAME, username)
                }
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowingFragment.USERNAME, username)
                }
            }
        }
        return fragment as Fragment
    }
}