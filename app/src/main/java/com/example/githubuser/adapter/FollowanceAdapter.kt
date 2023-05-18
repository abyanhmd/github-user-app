package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.FollowanceRowBinding
import com.example.githubuser.network.User

class FollowanceAdapter(private val listFollowance: ArrayList<User>) :
    RecyclerView.Adapter<FollowanceAdapter.ViewHolder>() {
    class ViewHolder(var followanceBinding: FollowanceRowBinding) :
        RecyclerView.ViewHolder(followanceBinding.root) {
        val tvUsername: TextView = followanceBinding.tvItemUsername
        val ivAvatar: ImageView = followanceBinding.imgItemPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val followanceRowBinding =
            FollowanceRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(followanceRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = listFollowance[position].username
        Glide.with(holder.itemView.context)
            .load(listFollowance[position].avatarUrl)
            .circleCrop()
            .into(holder.ivAvatar)
    }

    override fun getItemCount(): Int = listFollowance.size
}