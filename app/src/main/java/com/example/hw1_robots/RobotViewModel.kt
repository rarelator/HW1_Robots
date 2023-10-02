package com.example.hw1_robots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
const val MADE_PURCHASE_KEY = "MADE_PURCHASE_KEY"
class RobotViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "Instance of RobotViewModel created")
    }

    var lastPurchaseMade : Int
        get() = savedStateHandle.get(MADE_PURCHASE_KEY) ?: 0
        set(value) = savedStateHandle.set(MADE_PURCHASE_KEY, value)

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