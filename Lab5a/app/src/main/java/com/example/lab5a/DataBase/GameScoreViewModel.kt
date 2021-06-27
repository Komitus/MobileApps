package com.example.lab5a.DataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameScoreViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<GameScore>>
    private val repo: GameScoreRepo

    init{
        val gameScoreDAO = ScoresDB.getDatabase(application).gameScoreDAO()
        repo = GameScoreRepo(gameScoreDAO)
        readAllData = repo.readAllData
    }

    fun addGameScore(gameScore: GameScore){
        viewModelScope.launch(Dispatchers.IO){
            repo.addGameScore(gameScore)
        }
    }
}