package com.example.hangman

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hangman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedWord : String
    private lateinit var wordToDisplay : StringBuilder
    private lateinit var array: Array<String>
    private var failures : Int = 0
    private var gueessedLetters : Int = 0
    private lateinit var allButtons : ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        array = resources.getStringArray(R.array.word_bank)
        allButtons = (findViewById<LinearLayout>(R.id.linearLayout)).touchables
        initializeWord()
    }

    fun onButtonClicked(view : View){

        val tmp : Button = view as Button
        val letter : Char = tmp.text.toString()[0]
        var counter : Int = 0
        var i : Int = 0
        for(i in 0..selectedWord.lastIndex){
            if(selectedWord[i] == letter){
                wordToDisplay[2*i+1] = selectedWord[i]
                counter++
                gueessedLetters++
            }
        }
        binding.textView.text = wordToDisplay.toString()
        view.setEnabled(false)
        view.setBackgroundColor(Color.YELLOW)
        if(counter==0) failures++

        if(failures==8){
            Toast.makeText(this, "You lose, word:\n   "+selectedWord, Toast.LENGTH_SHORT).show()
            initializeWord()
        }
        if(gueessedLetters==selectedWord.length) {
            Toast.makeText(this, "You won", Toast.LENGTH_SHORT).show()
            initializeWord()
        }
        when(failures){
            1 -> binding.imageView.setImageResource(R.drawable.hangman01)
            2 -> binding.imageView.setImageResource(R.drawable.hangman02)
            3 -> binding.imageView.setImageResource(R.drawable.hangman03)
            4 -> binding.imageView.setImageResource(R.drawable.hangman04)
            5 -> binding.imageView.setImageResource(R.drawable.hangman05)
            6 -> binding.imageView.setImageResource(R.drawable.hangman06)
            7 -> binding.imageView.setImageResource(R.drawable.hangman07)
        }
    }

    private fun initializeWord(){

        wordToDisplay = StringBuilder("")
        failures = 0
        gueessedLetters = 0
        binding.imageView.setImageResource(R.drawable.hangman00)
        val random = (0..array.lastIndex).random()
        selectedWord = array[random]
        for(i in 1..selectedWord.length) wordToDisplay.append(" _")
        binding.textView.text = wordToDisplay
        for(i in 0..allButtons.lastIndex) allButtons[i].setEnabled(true)

    }
}







