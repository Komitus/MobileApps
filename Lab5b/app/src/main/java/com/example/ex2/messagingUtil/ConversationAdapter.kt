package com.example.ex2.messagingUtil

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ex2.databinding.ConversationRowBinding

class ConversationAdapter(private val currentUser: String): RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {

    private var messages = mutableListOf<SingleMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ConversationRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(messages[position].userId == currentUser){
            holder.binding.conversationCurrentUser.visibility = View.VISIBLE
            holder.binding.conversationCurrentUser.text = messages[position].message
        } else {
            holder.binding.conversationReceiver.text = messages[position].message
            holder.binding.conversationReceiver.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessageToShow(singleMessage: SingleMessage){

        if(!messages.contains(singleMessage)){
                messages.add(singleMessage)
                notifyItemInserted(itemCount-1)
        }
    }

    inner class ViewHolder(val binding: ConversationRowBinding): RecyclerView.ViewHolder(binding.root) {}
}