package com.example.lab5a.Game

import android.graphics.Paint
import android.graphics.RectF


abstract class GameObject(private val startX: Float, private val startY: Float,
                          val width: Float, val height: Float, val color: Paint) {

    var x = startX
    var y = startY
    var shape = RectF(x-width/2f, y-height/2f, x+width/2f, y+height/2f)

    open fun moveX(dx: Float) {
        x += dx
        shape.right += dx
        shape.left += dx
    }

    fun moveY(dy: Float) {
        y += dy
        shape.top += dy
        shape.bottom += dy
    }

    fun moveToPos(newX: Float, newY: Float) {
        x = newX
        y = newY
        shape.apply {
            left = x-width/2f
            top = y-height/2f
            right = x+width/2f
            bottom = y+height/2f
        }
    }

    fun reset() {
        moveToPos(startX, startY)
    }


}
