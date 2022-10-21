package com.labmacc.compass2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlin.math.PI
import kotlin.math.atan2

val TAG = "MYDEBUG"


class MainActivity : AppCompatActivity(),SensorEventListener2 {

    var size = 2f
    lateinit var sensorManager : SensorManager
    var mLastRotationVector = FloatArray(3)
    var mRotationMatrix = FloatArray(9)
    val a = 0.95f
    var yaw = 0f

    lateinit var view : CompassView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        size*=160*resources.displayMetrics.density
        Log.i(TAG,""+resources.displayMetrics.density)
        view = CompassView(this,size)
        setContentView(view)
    }


    override fun onResume() {
        super.onResume()
        //Register the rotation vector sensor to the listener
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        //Unregister as the app is pausing, so no compass is displayed
        sensorManager.unregisterListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR))
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        mLastRotationVector = p0?.values?.clone()!! //Get last rotation vector

        Log.i(TAG,""+mLastRotationVector[0]+""+mLastRotationVector[1]+" "+mLastRotationVector[2])

        //Compute the rotation matrix from the rotation vector
        SensorManager.getRotationMatrixFromVector(mRotationMatrix,mLastRotationVector)

        //Calculate the yaw angle, see slides of the lesson——
        yaw = a*yaw+(1-a)* atan2(mRotationMatrix[1],mRotationMatrix[4]) *180f/ PI.toFloat()
        view.doInvalidate(yaw)


    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//        TODO("Not yet implemented")
    }

    override fun onFlushCompleted(p0: Sensor?) {
  //      TODO("Not yet implemented")
    }
}