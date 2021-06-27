package com.example.ex2.userPanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.ex2.databinding.FragmentUserProfileBinding
import com.google.firebase.database.*

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private var userProfile : User? = null

    private lateinit var firebaseUser : FirebaseUser
    private lateinit var dbReference : DatabaseReference
    private lateinit var userID : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        dbReference = FirebaseDatabase.getInstance().getReference("Users")
        userID = firebaseUser.uid

        dbReference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userProfile = snapshot.getValue(User::class.java)

                if(userProfile != null){
                    fillTextViews()
                } else {
                    Toast.makeText(view?.context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(view?.context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }

        })

    }
    private fun fillTextViews(){
        binding.upWelcomeNick.text = "Welcome, "+userProfile!!.nickName+"!"
        binding.upFullName.text = userProfile!!.fullName
        binding.upEmail.text = userProfile!!.email
        binding.upAge.text = userProfile!!.age.toString()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        if(userProfile != null){
            fillTextViews()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}