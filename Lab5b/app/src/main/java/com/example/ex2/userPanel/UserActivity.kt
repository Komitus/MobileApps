package com.example.ex2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ex2.messagingUtil.ConversationActivity
import com.example.ex2.messagingUtil.MessagesFragment
import com.example.ex2.messagingUtil.UserWithUID
import com.example.ex2.userPanel.UserProfileFragment

class UserActivity : AppCompatActivity() {

    lateinit var userProfileFragment: UserProfileFragment
    lateinit var messagesFragment: MessagesFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        userProfileFragment = UserProfileFragment()
        messagesFragment = MessagesFragment()
        makeCurrentFragment(messagesFragment)
    }


    fun onClickMakeFragment(v: View) {
        when (v.id) {
            R.id.makeMessagesFragment -> makeCurrentFragment(messagesFragment)
            R.id.makeProfileFragment -> makeCurrentFragment(userProfileFragment)
        }
    }
    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment!!)
            addToBackStack(null)
            commit()
        }
    }

    fun startConversation(userToMessage: UserWithUID){
        val myIntent = Intent(this, ConversationActivity::class.java)
        myIntent.putExtra("userToMessage", userToMessage)
        startActivity(myIntent)
    }
}


