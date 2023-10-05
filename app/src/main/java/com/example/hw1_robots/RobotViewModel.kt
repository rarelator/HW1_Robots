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

    // Used by both MainActivity and RobotPurchase
    val rewards : Map<String, Int>
        get() = mapOf("A" to 1, "B" to 2, "C" to 3,
            "D" to 3, "E" to 4, "F" to 4, "G" to 7)

    // Get 3 random rewards
    val rewardNames : MutableList<String> = rewards.keys.toList().asSequence().shuffled().take(3).toMutableList()

    // Initialize the purchases map with the turn count being the keys
    // and a list of reward purchases as the values
    var purchases: Map<Int, ArrayList<String>> = mapOf(1 to arrayListOf(), 2 to arrayListOf(), 3 to arrayListOf() )

    var robotEnergies : MutableMap<Int, Int> = mutableMapOf(1 to 0, 2 to 0, 3 to 0)

    //Mainly used in MainActivity
    val purchasesMade : MutableList<String>
        get() = purchases[getTurnCount()] ?: mutableListOf()

    // Mainly used in RobotPurchase
    var robotEnergy = 0

    var enabledButtons : ArrayList<Boolean> = arrayListOf(true, true, true)

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