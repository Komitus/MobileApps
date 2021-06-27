package com.example.lab5a

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5a.DataBase.GameScore
import com.example.lab5a.DataBase.GameScoreViewModel
import com.example.lab5a.Game.GameActivity


class MainActivity : AppCompatActivity() {

    private lateinit var dBViewModel: GameScoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener { startNewGame() }

        val adapter = RecycleAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //db
        dBViewModel = GameScoreViewModel(this.application)//ViewModelProvider(this).get(GameScoreViewModel::class.java)
        dBViewModel.readAllData.observe(this, Observer { gameScore->adapter.setData(gameScore) })
    }
    fun startNewGame(){
        val myIntent = Intent(this, GameActivity::class.java)
        startActivityForResult(myIntent, 2137)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2137 && data != null) {
            val retData = data?.getSerializableExtra("retData") as GameScore
            dBViewModel.addGameScore(retData)
        }
    }

}