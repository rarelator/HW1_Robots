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

    // Initialize the purchases map with the turn count being the keys
    // and a list of reward purchases as the values
    var purchases: Map<Int, MutableList<String>> = mapOf(1 to mutableListOf(""), 2 to mutableListOf(""), 3 to mutableListOf("") )

    var rewards : Map<String, Int> = mapOf("Reward A" to 1, "Reward B" to 2, "Reward C" to 3,
        "Reward D" to 3, "Reward E" to 4, "Reward F" to 4, "Reward G" to 7)

    val lastPurchaseMade : String
        get() = purchases[getTurnCount()]?.last() ?: ""

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