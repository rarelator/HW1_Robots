package com.example.hw1_robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var yellowImg : ImageView
    private lateinit var rewardAButton: Button
    private lateinit var rewardBButton: Button
    private lateinit var rewardCButton: Button
    private lateinit var availableEnergy : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yellowImg = findViewById(R.id.yellowRobot)
        rewardAButton = findViewById(R.id.rewardA)
        rewardBButton = findViewById(R.id.rewardB)
        rewardCButton = findViewById(R.id.rewardC)
        availableEnergy = findViewById(R.id.availableEnergy)



        Log.d(TAG, "onCreate() entered")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() entered")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() entered")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() entered")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() entered")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() entered")
    }

    private fun toggleImage() {

    }
}