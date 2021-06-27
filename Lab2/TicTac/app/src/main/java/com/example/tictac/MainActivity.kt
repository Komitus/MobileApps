package com.example.tictac

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tictac.databinding.ActivityBoard3x3Binding
import com.example.tictac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun boardFor3(view: View) {
        val myIntent = Intent(this, Board3x3::class.java)
        myIntent.putExtra("size", 3)
        startActivityForResult(myIntent, 1)
    }
    fun boardFor5(view: View) {
        val myIntent = Intent(this, Board5x5::class.java)
        myIntent.putExtra("size", 5)
        startActivityForResult(myIntent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 || requestCode == 2) {
            data?.getStringExtra("result")?.let { lastWinner(it) };
        }
    }

    private fun lastWinner( value : String){

        if(value == "0"){
            binding.winnerText.text = "CROSS"
        }
        else if(value == "1"){
            binding.winnerText.text = "CIRCLE"
        }
        else
            binding.winnerText.text = value
    }

}

