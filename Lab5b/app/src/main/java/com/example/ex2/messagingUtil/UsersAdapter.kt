package com.example.ex2.messagingUtil

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ex2.UserActivity
import com.example.ex2.databinding.RecycleRowBinding

class UsersAdapter(private val userActivity: FragmentActivity): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    var users = mutableListOf<UserWithUID>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecycleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userListNickName.text = users[position].nickName
        holder.itemView.setOnClickListener{
            Log.println(Log.INFO, "INFO", "Conversation started")
            (userActivity as UserActivity).startConversation(users[position])
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
    //this is why we don't have duplciates, i suggest to add timestamp to messages to differ them
    fun addUser(userWithUID: UserWithUID){
        if(!users.contains(userWithUID)){
            users.add(userWithUID)
            notifyItemInserted(itemCount-1)
        }
    }

    inner class ViewHolder(val binding: RecycleRowBinding): RecyclerView.ViewHolder(binding.root) {}


}