package com.example.tictac

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictac.databinding.ActivityBoard3x3Binding

enum class OccupiedBy {
    CIRCLE, CROSS, EMPTY
}
class Board3x3 : AppCompatActivity() {

    private lateinit var binding: ActivityBoard3x3Binding
    private lateinit var positions: Array<Array<OccupiedBy>>
    private var player = 0
    private var numbOfMoves = 0
    private var n : Int = 0   //may be arraIdxOfBoudnExcpet thats good because i want to get value froom Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoard3x3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        n = intent.getIntExtra("size",3)
        positions = Array(n) {Array(n) { OccupiedBy.EMPTY} }

    }


    fun onButtonClicked(view: View) {

        val x : Int = view.tag.toString().toInt()/10
        var y : Int = view.tag.toString().toInt()%10


        if(player.equals(0)){
            view.foreground = getDrawable(R.drawable.cross)
            positions[x][y] = OccupiedBy.CROSS
            numbOfMoves++
            checkForWin(x,y, OccupiedBy.CROSS)
        } else{
            view.foreground = getDrawable(R.drawable.circle)
            positions[x][y] = OccupiedBy.CIRCLE
            numbOfMoves++
            checkForWin(x,y, OccupiedBy.CIRCLE)
        }
        view.isEnabled = false
        player = (player+1)%2

       //view.setForeground(getDrawable(R.drawable.circle))


    }

    private fun checkForWin (x : Int, y : Int, figure : OccupiedBy) {

        //vertically
        for(i in 0 until n){
            if(positions[x][i]!=figure)
                break;
            if(i == n-1)
                endGame(figure.toString())
        }
        //horizontally
        for(i in 0 until n){
            if(positions[i][y]!=figure)
                break;
            if(i == n-1)
                endGame(figure.toString())
        }
        //diagonally
        if(x==y){
            for(i in 0 until  n){
                if(positions[i][i]!=figure)
                    break;
                if(i == n-1)
                    endGame(figure.toString())
            }
        }
        //anti-diagonally
        if(x+y == n-1){
            for(i in 0 until n){
                if(positions[i][(n-1)-i] != figure)
                    break;
                if(i==n-1)
                    endGame(figure.toString())
            }
        }
        if(numbOfMoves == n*n-1)
            endGame("DRAW")

    }

    private fun endGame(message : String){
        val returnIntent = Intent(this, MainActivity::class.java)
        returnIntent.putExtra("result", message) //Optional parameters
        setResult(Activity.RESULT_OK , returnIntent)
        finish()
    }


}
