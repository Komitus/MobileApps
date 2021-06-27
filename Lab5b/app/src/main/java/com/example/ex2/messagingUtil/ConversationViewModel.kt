package com.example.ex2.messagingUtil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class ConversationViewModel(private val refChildName: String) : ViewModel(){

    private val dbChats = FirebaseDatabase.getInstance().getReference("Chats")

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _conversation = MutableLiveData<SingleMessage>()
    val conversation: LiveData<SingleMessage> get() = _conversation

    fun addMessage(singleMessage: SingleMessage){
        Log.println(Log.ASSERT, "RefName in addMessage", refChildName)
        val key = dbChats.push().key!!
        // updateChildren(mutableMapOf(Pair<String, Map<String, String>>("timeStamp", ServerValue.TIMESTAMP)) as Map<String, Any>)
        dbChats.child(refChildName).child(key).setValue(singleMessage).addOnCompleteListener {
            if(it.isSuccessful){
                dbChats.child(refChildName).child(key).
                child("timeStamp").setValue(ServerValue.TIMESTAMP)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            _result.value = null
                        } else {
                            _result.value = task.exception
                        }
                    }
            } else {
                _result.value = it.exception
            }
        }
    }

    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val message = snapshot.getValue(SingleMessage::class.java)
            Log.println(Log.ASSERT, "Message:", message?.message.toString())
            _conversation.value = message!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    fun getRealTimeUpdate(){
        Log.println(Log.ASSERT, "RefName in update", refChildName)
        dbChats.child(refChildName).orderByChild("timeStamp").addChildEventListener(childEventListener)
    }

    override fun onCleared(){
        super.onCleared()
        dbChats.child(refChildName).orderByChild("timeStamp").removeEventListener(childEventListener)
    }
}