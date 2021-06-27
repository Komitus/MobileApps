package com.example.lab5a.DataBase

import androidx.lifecycle.LiveData

class GameScoreRepo(private val gameScoreDAO: GameScoreDAO) {

    val readAllData: LiveData<List<GameScore>> = gameScoreDAO.getAll()

    suspend fun addGameScore(gameScore: GameScore){
        gameScoreDAO.addGameScore(gameScore)
    }
}