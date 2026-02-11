package com.example.basicapp.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.basicapp.R
import com.example.basicapp.data.model.GithubUser
import com.example.basicapp.databinding.ItemUserBinding
import kotlin.properties.Delegates

interface OnUserClickListener {
    fun onUserClick(user: GithubUser)
}


class UserAdapter(private val listener: OnUserClickListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList = listOf<GithubUser>()

    private val expandedItems = mutableSetOf<Int>()

    fun updateData(newUsers: List<GithubUser>) {
        userList = newUsers
        notifyDataSetChanged()  // Tell RecyclerView to refresh
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: GithubUser) {
            binding.userLogin.text = user.login
            binding.userType.text = user.type

            binding.userId.text = user.id.toString()
            binding.userProfileUrl.text = user.html_url
            binding.userAvatarUrl.text = user.avatar_url

            binding.avatarProgress.visibility = View.VISIBLE
            binding.userAvatar.load(user.avatar_url) {
                listener(
                    onSuccess = { _, _ -> binding.avatarProgress.visibility = View.GONE },
                    onError = { _, _ -> binding.avatarProgress.visibility = View.GONE }
                )
            }

            val isExpanded = expandedItems.contains(user.id)
            binding.expandableSection.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.expandIcon.rotation = if (isExpanded) 180f else 0f

            //dropdown button click
            binding.expandIcon.setOnClickListener {
                if (isExpanded) {
                    expandedItems.remove(user.id)
                } else {
                    expandedItems.add(user.id)
                }
                notifyItemChanged(adapterPosition)
            }

            //whole card click->details
            binding.root.setOnClickListener {
                listener.onUserClick(user)
            }
        }
    }
}
