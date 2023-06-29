package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.UserRowBinding
import com.example.githubuser.model.UserDetailActivity
import com.example.githubuser.network.User

class ListUserAdapter(private val listUsers: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    class ViewHolder(private var listUserBinding: UserRowBinding) :
        RecyclerView.ViewHolder(listUserBinding.root) {
        val tvName: TextView = listUserBinding.tvItemUsername
        val ivAvatar: ImageView = listUserBinding.imgItemPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listUserBinding =
            UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listUserBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listUsers[position].username
        Glide.with(holder.itemView.context)
            .load(listUsers[position].avatarUrl)
            .circleCrop()
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.USERNAME, listUsers[position].username)
            intent.putExtra(UserDetailActivity.ID, listUsers[position].id)
            intent.putExtra(UserDetailActivity.AVATAR_URL, listUsers[position].avatarUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUsers.size
}