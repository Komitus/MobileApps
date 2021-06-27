package com.example.ex2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ex2.userPanel.RegisterUser
import com.example.ex2.UserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_up_activity)
        editTextEmail = findViewById<EditText>(R.id.loginEmail)
        editTextPassword = findViewById<EditText>(R.id.loginPassword)
        mAuth =  Firebase.auth
    }

    fun onClick(v: View){
        when(v.id){
            R.id.loginRegister ->
              startActivity(Intent(this, RegisterUser::class.java))
            R.id.loginButton -> login()
        }
    }

    private fun login() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, UserActivity::class.java))
            } else {
                Toast.makeText(this, "Failed to login!", Toast.LENGTH_LONG)
            }
        }
    }
}