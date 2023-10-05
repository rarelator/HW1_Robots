package com.example.hw1_robots

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels

private const val EXTRA_ROBOT_ENERGY = "com.example.hw1_robots.robot.current_robot_energy"
const val EXTRA_ROBOT_PURCHASE_MADE = "com.example.hw1_robots.robot.item_purchased"
const val EXTRA_ROBOT_PURCHASE_MADE_LIST = "com.example.hw1_robots.robot.items_purchased_list"
private const val EXTRA_ROBOT_TURN = "com.example.hw1_robots.robot.current_turn"
private const val TAG = "RobotPurchase"
class RobotPurchase : AppCompatActivity() {
    private lateinit var rewardButtons : ArrayList<Button>
    private lateinit var rewardEnergies : ArrayList<TextView>
    private lateinit var availableEnergy : TextView
    private lateinit var robot: ImageView
    private var currentTurn = 0
    private var robotEnergy = 0
    private val robotViewModel: RobotViewModel by viewModels()

    private var robotImages = listOf(
        R.drawable.king_of_detroit_robot_red_large,
        R.drawable.king_of_detroit_robot_white_large,
        R.drawable.king_of_detroit_robot_yellow_large)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        robot = findViewById(R.id.robot)
        currentTurn = intent.getIntExtra(EXTRA_ROBOT_TURN, 0)
        robot.setImageResource(robotImages[currentTurn])

        rewardButtons = arrayListOf(
            findViewById(R.id.reward1),
            findViewById(R.id.reward2),
            findViewById(R.id.reward3)
        )

        rewardEnergies = arrayListOf(
            findViewById(R.id.reward1Energy),
            findViewById(R.id.reward2Energy),
            findViewById(R.id.reward3Energy)
        )

        // Gets 3 random rewards
        robotViewModel.rewardNames.sort()

        for (i in 0..2) {
            val s = "Reward${robotViewModel.rewardNames[i]}"
            rewardButtons[i].text = s
            rewardEnergies[i].text = robotViewModel.rewards[robotViewModel.rewardNames[i]].toString()
        }

        availableEnergy = findViewById(R.id.availableEnergy)
        robotEnergy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 6)

        for (i in 0..2) {
            if (!robotViewModel.enabledButtons[i]) {
                robotEnergy = robotViewModel.robotEnergy
                setItemPurchased(robotViewModel.rewardNames[i], true)
            }
        }

        availableEnergy.text = robotEnergy.toString()
        Log.d(TAG, robotEnergy.toString())

        for (i in 0..2) {
            rewardButtons[i].isEnabled = robotViewModel.enabledButtons[i]
            rewardButtons[i].setOnClickListener { view: View ->
                if (makePurchase(robotViewModel.rewardNames[i])) {
                    robotViewModel.enabledButtons[i] = false
                    rewardButtons[i].isEnabled = false
                }
            }
        }
    }

    private fun setItemPurchased(robotPurchasesMade : String, rotated : Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ROBOT_PURCHASE_MADE, robotPurchasesMade)
        }

        // Check if there's an existing ArrayList of results, or create a new one
        val resultList : ArrayList<String> = (intent.getStringArrayListExtra(
            EXTRA_ROBOT_PURCHASE_MADE_LIST) ?: ArrayList()).apply {
            if (!rotated) add(robotPurchasesMade)
        }

        data.putStringArrayListExtra(EXTRA_ROBOT_PURCHASE_MADE_LIST, resultList)
        setResult(Activity.RESULT_OK, data)
    }

    // Like a constructor
    // Solves encapsulation
    companion object {
        fun newIntent(packageContext: Context, robotEnergy : Int, currentTurn : Int) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robotEnergy)
                putExtra(EXTRA_ROBOT_TURN, currentTurn)
                putStringArrayListExtra(EXTRA_ROBOT_PURCHASE_MADE_LIST, ArrayList())
            }
        }
    }

    private fun makePurchase(reward : String) : Boolean{
        val costOfPurchase = robotViewModel.rewards[reward] ?: 0
        if (robotEnergy >= costOfPurchase){
            val s1 = getString(R.string.purchased)
            val s2 = "Reward $reward $s1"
            robotEnergy -= costOfPurchase
            availableEnergy.text = robotEnergy.toString()
            robotViewModel.robotEnergy = robotEnergy
            setItemPurchased(reward, false)
            Toast.makeText(this, s2, Toast.LENGTH_SHORT).show()
            return true
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
            return false
        }
    }

}