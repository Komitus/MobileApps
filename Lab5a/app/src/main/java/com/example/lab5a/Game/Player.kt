package com.example.lab5a.Game

import android.graphics.Paint

class Player(private val startX: Float, private val startY: Float,
           private val startWidth: Float, private val startHeight: Float, color: Paint, private val calculatedWidth: Float
):
    GameObject(startX, startY, startWidth, startHeight, color) {
        var score : Int = 0
        override fun moveX(dx: Float) {
            val deltaLeft = shape.left + dx/2f
            val deltaRight = shape.right + dx/2f
            if(deltaRight < calculatedWidth && deltaLeft >= 0f){
                super.moveX(dx)
            }
        }
    }


