package com.example.ex2.messagingUtil

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ex2.UsersListViewModel
import com.example.ex2.databinding.ActivityConversationBinding
import com.example.ex2.userPanel.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class ConversationActivity : AppCompatActivity() {

    private var _binding: ActivityConversationBinding? = null
    private val binding get() = _binding!!

    private var viewModel: ConversationViewModel? = null
    private lateinit var adapter : ConversationAdapter

    private lateinit var currentUserId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myIntent = intent // gets the previously created intent
        val userToMessage = myIntent.getSerializableExtra("userToMessage") as UserWithUID

        val firebaseUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = firebaseUser.uid

        val tmpReference = FirebaseDatabase.getInstance().getReference("Chats")

        val firebaseTask = tmpReference.child(currentUserId+"_"+userToMessage.uid)
            .limitToFirst(1).get().addOnCompleteListener (this){task->
                if (task.isSuccessful) {
                    if (task.result!!.getValue(SingleMessage::class.java) != null) {
                        viewModel = ConversationViewModel(currentUserId+"_"+userToMessage.uid)
                    } else {
                        viewModel = ConversationViewModel(userToMessage.uid+"_"+currentUserId)
                    }

                    _binding = ActivityConversationBinding.inflate(this.layoutInflater)
                    binding.conversationNick.text = userToMessage.nickName
                    binding.sendButton.setOnClickListener { sendMessage() }
                    adapter = ConversationAdapter(currentUserId)
                    binding.convRecycler.adapter = adapter
                    binding.convRecycler.layoutManager = LinearLayoutManager(this)
                    setContentView(binding.root)

                    viewModel!!.result.observe(this, Observer {
                        if(it != null){
                            Toast.makeText(this, "Message send failed!", Toast.LENGTH_SHORT).show()
                        }
                    })

                    viewModel!!.conversation.observe(this, Observer {
                        Log.println(Log.ERROR, "MESSAGE_CHANGED", it.message.toString())
                        adapter.addMessageToShow(it)
                        binding.convRecycler.scrollToPosition(adapter.itemCount-1)
                    })
                    viewModel!!.getRealTimeUpdate()
                }
            }
    }

    private fun sendMessage(){
        val msg = binding.messageToSend.text.toString()
        if(msg.isEmpty()) return
        val singleMessage = SingleMessage()
        singleMessage.message = msg
        singleMessage.userId = currentUserId
        viewModel!!.addMessage(singleMessage)
    }
}