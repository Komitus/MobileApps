package com.example.lab5a.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "game_score")
data class GameScore(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo(name = "winner") var winner : String,
    @ColumnInfo(name = "player_bottom") var player_bottom: Int,
    @ColumnInfo(name = "player_top") var player_top: Int
) : Serializable