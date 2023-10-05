package com.example.hw1_robots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "Instance of RobotViewModel created")
    }

    // Initialize the purchases map with the turn count being the keys
    // and a list of reward purchases as the values
    var purchases: Map<Int, MutableList<Int>> = mapOf(1 to mutableListOf(0), 2 to mutableListOf(0), 3 to mutableListOf(0) )

    val lastPurchaseMade : Int
        get() = purchases[getTurnCount()]?.last() ?: 0

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