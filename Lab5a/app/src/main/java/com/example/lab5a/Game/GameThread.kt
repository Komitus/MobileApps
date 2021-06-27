package com.example.lab5a.Game

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(val surfaceHolder: SurfaceHolder, val gameView: GameView)
    : Thread() {

    var running = false
    private var canvas : Canvas? = null
    var targetFPS = 50f

    override fun run() {
        var startTime : Long
        var timeMillis : Long
        var waitTime: Long
        var targetTime = (1000/targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            var canvas = surfaceHolder.lockCanvas()
            gameView.draw(canvas)
            gameView.update()
            surfaceHolder.unlockCanvasAndPost(canvas)
            timeMillis = (System.nanoTime() - startTime)/ 1000000
            waitTime = targetTime - timeMillis

            if (waitTime >= 0)
                sleep(waitTime)
        }
    }
}