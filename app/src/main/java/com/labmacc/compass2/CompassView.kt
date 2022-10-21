package com.labmacc.compass2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.withRotation

class CompassView(context: Context?, var size:Float) : View(context) {

    var yaw = 0f
    var compass: Bitmap

    init {
        compass = ResourcesCompat.getDrawable(
            resources, R.drawable.compass,
            null
        )?.toBitmap(size.toInt(), size.toInt())!!
    }

    fun doInvalidate(y: Float) {
        yaw = y
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i(TAG, "drawing" + System.currentTimeMillis())
        with(canvas) {
            drawColor(Color.YELLOW)
            withRotation(-yaw, width / 2f, height / 2f) {
                drawBitmap(compass, (width - size) / 2f, (height - size) / 2f, null)
            }
        }
    }
}