package com.example.lab5a.Game

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.abs
import kotlin.random.Random

class DataModel(val height: Float, val width: Float) {

    //i consider game vertically
    val ball: Ball

    //player in the bottom
    val playerBottom: Player

    //player at the top
    val playerTop: Player
    val paddleWidth = width / 5f
    val paddleHeight = height / 100f
    val sliceSize = paddleWidth/9f
    var dx = 8f
    var dy = 8f
    var collision : Boolean = false
    init {
        val ballDiameter = width/12f
        val paintBall = Paint()
        paintBall.color = Color.WHITE
        paintBall.isAntiAlias = true
        ball =
            Ball(width / 2f, height / 2f, ballDiameter, ballDiameter, paintBall)
        val paddlePaddingVertical = 1.3f * ballDiameter
        val paintPlayer = Paint()
        paintPlayer.color = Color.LTGRAY
        playerBottom = Player(
            width / 2f, height - paddlePaddingVertical,
            paddleWidth, paddleHeight, paintPlayer, width
        )
        playerTop = Player(
            width / 2f, paddlePaddingVertical,
            paddleWidth, paddleHeight, paintPlayer, width
        )
    }

    fun updateBallPos() {
        ball.moveX(dx)
        ball.moveY(dy)

        if (ball.shape.bottom >= height) {
            playerTop.score++
            resetDeltas()
            ball.reset()
            return
        }
        else if (ball.shape.top <= 0f) {
            playerBottom.score++
            resetDeltas()
            ball.reset()
            return
        }
        if (ball.shape.left <= 0 || ball.shape.right >= width)
            dx = -dx
    }

    fun checkCollision() {

        val player: Player = when {
            RectF.intersects(playerBottom.shape, ball.shape) -> playerBottom
            RectF.intersects(playerTop.shape, ball.shape) -> playerTop
            else -> return
        }

        if (dy <= 0 && dy > -20) {
            dy -= 1f
        } else if(dy < 20) {
            dy += 1f
        }
        var signOfDx : Float
        if(dx < 0) signOfDx = -1f
        else signOfDx = 1f

        when (abs(player.shape.right - ball.x)) {
            in 0f..sliceSize -> dx = 8f
            in sliceSize..sliceSize * 2f -> dx = 6f
            in 2f * sliceSize..sliceSize * 3f -> dx = 4f
            in sliceSize * 3..sliceSize * 4f -> dx = 2f
            in sliceSize * 4f..sliceSize * 5f -> dx = 0f
            in sliceSize * 5f..sliceSize * 6f -> dx = 2f
            in sliceSize * 7f..sliceSize * 8f -> dx = 4f
            in sliceSize * 8f..sliceSize * 9f -> dx = 6f
            in sliceSize * 9f..sliceSize * 10f -> dx = 8f
        }

        dx = dx*signOfDx
        dy = -dy
    }

    fun resetDeltas(){
        dx = Random.nextInt(-9, 9).toFloat()
        dy = arrayListOf<Int>(-8, 8)[Random.nextInt(0,2)].toFloat()
    }
}