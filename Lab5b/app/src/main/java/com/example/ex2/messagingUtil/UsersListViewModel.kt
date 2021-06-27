package com.example.ex2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ex2.messagingUtil.UserWithUID
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class UsersListViewModel : ViewModel(){
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Users")

    private val _usersList = MutableLiveData<UserWithUID>()
    val usersList: LiveData<UserWithUID> get() = _usersList

    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val key = snapshot.key as String
            val nickName = snapshot.child("nickName").value as String
            _usersList.value = UserWithUID(key, nickName)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    fun getRealTimeUpdate(){
        dbUsers.addChildEventListener(childEventListener)
    }

    override fun onCleared(){
        super.onCleared()
        dbUsers.removeEventListener(childEventListener)
    }
}