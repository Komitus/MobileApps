package com.example.lab5a.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameScoreDAO {

    @Query("select * from game_score order by id desc")
    fun getAll() : LiveData<List<GameScore>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGameScore(gameScore : GameScore)

}