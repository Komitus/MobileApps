package com.example.lab5a.Game


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log

import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.lab5a.DataBase.GameScore
import com.example.lab5a.MainActivity


class GameView(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet), SurfaceHolder.Callback
{
    private var thread : GameThread
    private lateinit var data : DataModel
    private lateinit var ball : Ball
    private lateinit var playerBottom : Player
    private lateinit var playerTop : Player
    private var playerSpeed : Float = 40f
    private lateinit var line : RectF
    private val paintLine = Paint()
    private val paintText = Paint()

    init {
        paintText.textSize = 60f
        paintText.color = Color.WHITE
        paintText.isAntiAlias = true
        val tmp = context as GameActivity
        data = DataModel(tmp.childHeight, tmp.childWidth)
        data.resetDeltas()
        ball = data.ball
        playerBottom = data.playerBottom
        playerTop = data.playerTop
        line = RectF(0f, tmp.childHeight/2f+10f, tmp.childWidth, tmp.childHeight/2f-10f)
        paintLine.color = Color.DKGRAY
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //i have to do this there bcs otherwise i don't get width and height
        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running = false
        thread.join()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return

        canvas.drawRect(playerBottom.shape, playerBottom.color)
        canvas.drawRect(playerTop.shape, playerTop.color)
        canvas.drawRect(line, paintLine)
        canvas.drawOval(ball.shape, ball.color)
        canvas.drawText("\uD83D\uDD2E"+playerBottom.score, width-120f, height-20f, paintText)
        canvas.scale(1f, -1f)
        canvas.drawText("\uD83D\uDD2E"+playerTop.score, 0f, 0f-20f, paintText)

    }

    fun update() {
        data.updateBallPos()
        data.checkCollision()
        if(data.playerBottom.score == 3){
            retFromIntent("Bottom")
        } else if (data.playerTop.score == 3){
            retFromIntent("Top")
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        if(event == null) return false

        var delta = playerSpeed
        val pointerCount = event.pointerCount

        for (i in 0 until pointerCount)
        {
            val x = event.getX(i)
            val y = event.getY(i)
            val action = event.actionMasked

            if (y > height/2f + 300f)
            {
                if (x < playerBottom.shape.centerX()) delta = -delta
                    if (action == MotionEvent.ACTION_MOVE)
                        playerBottom.moveX(delta)
            }
            else if (y < height/2f - 300f ) {
                if (x < playerTop.shape.centerX()) delta = -delta
                    if (action == MotionEvent.ACTION_MOVE)
                        playerTop.moveX(delta)
            }
        }
        return true
    }

    //called from GameActivity
    fun pause() {
        thread.running = false
    }

    fun resume(){
        thread.running = true
        thread.start()
    }

    private fun retFromIntent(winner: String){
        thread.running = false
        Log.println(Log.ERROR, "Winner", winner)
        val retData = GameScore(0, winner, data.playerBottom.score,  data.playerTop.score)
        val returnIntent = Intent(this.context, MainActivity::class.java)
        returnIntent.putExtra("retData", retData)
        (context as GameActivity).setResult(Activity.RESULT_OK, returnIntent)
        (context as GameActivity).finish()
    }
}