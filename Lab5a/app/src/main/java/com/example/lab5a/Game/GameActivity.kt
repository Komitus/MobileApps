package com.example.lab5a.Game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5a.DataBase.GameScore
import com.example.lab5a.MainActivity
import com.example.lab5a.R


class GameActivity : AppCompatActivity() {
    private lateinit var gameView : GameView
    var childHeight: Float = 0f
    var childWidth: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        childHeight = displayMetrics.heightPixels.toFloat()
        childWidth = displayMetrics.widthPixels.toFloat()
        setContentView(R.layout.game_activity)
        gameView = findViewById<GameView>(R.id.gameView) as GameView

    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.game_activity)
    }


}