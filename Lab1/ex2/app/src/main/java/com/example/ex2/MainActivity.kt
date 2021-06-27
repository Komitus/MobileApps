package com.example.ex2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.example.ex2.databinding.ActivityMainBinding
import kotlin.random.Random

enum class Figure  {
    rock,
    paper,
    scissors
}
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var score = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
    private fun roll(){
        binding.score.setText("Score:\n"+score)
        binding.playersChoice.setTextColor(Color.RED)
        binding.playersChoice.setTextSize(TypedValue.COMPLEX_UNIT_SP,24F);
    }

    fun rockButtonHandler(view: View){

        val random = (0..2).random()
        var computerInput =  Figure.values().get(random)
        when(computerInput){
            Figure.rock -> {
                binding.computersChoice.setImageResource(R.drawable.rock)
            }
            Figure.paper -> {
                binding.computersChoice.setImageResource(R.drawable.paper)
                score--
            }
            Figure.scissors -> {
                score++
                binding.computersChoice.setImageResource(R.drawable.scissors)
            }

        }
        roll();
        binding.playersChoice.text = "Your choice:\n"+"Rock"
    }
    fun paperButtonHandler(view: View){
        val random = (0..2).random()
        var computerInput =  Figure.values().get(random)
        when(computerInput){
            Figure.rock -> {
                binding.computersChoice.setImageResource(R.drawable.rock)
                score++
            }
            Figure.paper -> {
                binding.computersChoice.setImageResource(R.drawable.paper)
            }
            Figure.scissors -> {
                binding.computersChoice.setImageResource(R.drawable.scissors)
                score--
            }

        }
        roll();
        binding.playersChoice.text = "Your choice:\n"+"Paper"

    }
    fun scissorsButtonHandler(view: View){
        val random = (0..2).random()
        var computerInput =  Figure.values().get(random)
        when(computerInput){
            Figure.rock -> {
                binding.computersChoice.setImageResource(R.drawable.rock)
                score--
            }
            Figure.paper -> {
                binding.computersChoice.setImageResource(R.drawable.paper)
                score++
            }
            Figure.scissors -> {
                binding.computersChoice.setImageResource(R.drawable.scissors)
            }

        }
        roll();
        binding.playersChoice.text = "Your choice:\n"+"Scissors"

    }






}