package com.example.lab5a.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameScore::class], version = 1, exportSchema = false)

abstract class ScoresDB : RoomDatabase() {

    abstract fun gameScoreDAO() : GameScoreDAO

    companion object{
        @Volatile
        private var INSTANCE: ScoresDB? = null

        fun getDatabase(context: Context) : ScoresDB{
            val tmpInstance = INSTANCE
            if(tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    ScoresDB::class.java,
                    "planner_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}