package com.example.hw1_robots

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val EXTRA_ROBOT_ENERGY = "com.example.hw1_robots.robot.current_robot_energy"
const val EXTRA_ROBOT_PURCHASE_MADE = "com.example.hw1_robots.robot.item_purchased"
class RobotPurchase : AppCompatActivity() {
    private lateinit var rewardAButton: Button
    private lateinit var rewardBButton: Button
    private lateinit var rewardCButton: Button
    private lateinit var availableEnergy : TextView
    private var robotEnergy = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        rewardAButton = findViewById(R.id.rewardA)
        rewardBButton = findViewById(R.id.rewardB)
        rewardCButton = findViewById(R.id.rewardC)
        availableEnergy = findViewById(R.id.availableEnergy)

        //robotEnergy = 2 // Hardcoded for now
        robotEnergy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 6)
        availableEnergy.setText(robotEnergy.toString())

        rewardAButton.setOnClickListener{view : View ->
            makePurchase(1)
        }
        rewardBButton.setOnClickListener{view : View ->
            makePurchase(2)
        }
        rewardCButton.setOnClickListener{view : View ->
            makePurchase(3)
        }
    }

    private fun setItemPurchased(robotPurchaseMade : Int) {
        val data = Intent().apply {
            putExtra(EXTRA_ROBOT_PURCHASE_MADE, robotPurchaseMade)
        }

        setResult(Activity.RESULT_OK, data)
    }

    // Like a constructor
    companion object {
        fun newIntent(packageContext: Context, robotEnergy : Int) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robotEnergy)
            }
        }
    }

    private fun makePurchase(costOfPurchase : Int){
        if (robotEnergy >= costOfPurchase){
            val s1 = when (costOfPurchase) {
                1 -> getString(R.string.reward_A_text)
                2 -> getString(R.string.reward_B_text)
                3 -> getString(R.string.reward_C_text)
                else -> getString(R.string.error_reward)
            }
            val s2 = getString(R.string.purchased)
            val s3 = "$s1 $s2"
            robotEnergy -= costOfPurchase
            availableEnergy.setText(robotEnergy.toString())
            setItemPurchased(costOfPurchase)
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}