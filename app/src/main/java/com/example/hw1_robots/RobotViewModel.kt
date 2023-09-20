package com.example.hw1_robots

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel() {
    init {
        Log.d(TAG, "Instance of RobotViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Instance of RobotViewModel about to be destroyed")
    }

    private var turnCount : Int = 0

    fun advanceTurn() {
        turnCount++
        if(turnCount > 3)
            turnCount = 1
    }

    fun getTurnCount() : Int {
        return turnCount
    }
}