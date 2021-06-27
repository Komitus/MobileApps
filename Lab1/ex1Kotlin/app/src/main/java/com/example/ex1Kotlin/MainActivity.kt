package com.example.ex1Kotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ex1Kotlin.databinding.ActivityMainBinding
import kotlin.ranges.random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var r1 = 0;
    private var r2 = 0;
    private var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        roll();
    }

    private fun roll() {
        binding.points.text = "Punkty:" + score;
        r1 = (0..10).random();
        r2 = (0..10).random();
        binding.buttonLeft.text = "" + r1;
        binding.buttonRight.text = "" + r2;
    }


    fun leftButton(view: View) {
        if (r1 >= r2) {
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        }
        else
        {
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();

    }
    fun rightButton(view: View) {
        if (r1 <= r2) {
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        }
        else
        {
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();
    }
}