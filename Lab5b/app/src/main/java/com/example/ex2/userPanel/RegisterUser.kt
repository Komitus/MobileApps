package com.example.ex2.userPanel

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ex2.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Integer.parseInt


class RegisterUser : AppCompatActivity(){

    private lateinit var mAuth: FirebaseAuth

    private lateinit var editTextFullName : EditText
    private lateinit var editTextAge : EditText
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var submitButton : Button
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNickName : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)

        editTextFullName = findViewById<EditText>(R.id.registerFullName)
        editTextAge = findViewById<EditText>(R.id.registerAge)
        editTextEmail = findViewById<EditText>(R.id.registerEmail)
        editTextPassword = findViewById<EditText>(R.id.registerPassword)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.GONE
        submitButton = findViewById<Button>(R.id.registerButton)
        submitButton.setOnClickListener { this.onClick(it) }
        editTextNickName = findViewById<EditText>(R.id.registerNick)
        mAuth = Firebase.auth
    }

    private fun onClick(v : View) {
        registerUser()
    }

    private fun registerUser(){
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val fullName = editTextFullName.text.toString().trim()
        val ageString = editTextAge.text.toString().trim()
        val nickName = editTextNickName.text.toString().trim()
        var age : Int = 0

        if(fullName.isEmpty()){
            editTextFullName.error = "Full name is required"
            editTextFullName.requestFocus()
            return
        }

        if(nickName.isEmpty()){
            editTextNickName.error = "Nick is required"
            editTextNickName.requestFocus()
            return
        }

        if(ageString.isEmpty()){
            editTextAge.error = "Age is required"
            editTextAge.requestFocus()
            return
        } else {
            try {
                age = parseInt(ageString)
                if(age <= 0 || age > 200){
                    editTextAge.error = "Bad age"
                    editTextAge.requestFocus()
                    return
                }
            } catch (e: NumberFormatException) {
                editTextAge.error = "Age must be number"
                editTextAge.requestFocus()
                return
            }
        }

        if(email.isEmpty()){
            editTextEmail.error = "Email is required"
            editTextEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.error = "Email not valid"
            editTextEmail.requestFocus()
            return
        }

        if(password.isEmpty()){
            editTextPassword.error = "Password is required"
            editTextPassword.requestFocus()
            return
        }

        if(password.length < 6){
            editTextPassword.error = "Password must be at least 6 signs long"
            editTextPassword.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                        val tmp = PairIDChat("", "")
                    val user = User(age, email, fullName, nickName)

                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user).addOnCompleteListener{
                            if (it.isSuccessful) {
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        this,
                                        "User has been registered successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        this,
                                        "Failed to register! Try again!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    progressBar.visibility = View.GONE
                                }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Failed to register! Try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                }
            }

    }

}
