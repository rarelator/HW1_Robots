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
import androidx.activity.viewModels

private const val EXTRA_ROBOT_ENERGY = "com.example.hw1_robots.robot.current_robot_energy"
const val EXTRA_ROBOT_PURCHASE_MADE = "com.example.hw1_robots.robot.item_purchased"
class RobotPurchase : AppCompatActivity() {
    private lateinit var reward1Button: Button
    private lateinit var reward2Button: Button
    private lateinit var reward3Button: Button
    private lateinit var availableEnergy : TextView
    private lateinit var reward1Energy : TextView
    private lateinit var reward2Energy : TextView
    private lateinit var reward3Energy : TextView
    private var robotEnergy = 0
    private val robotViewModel: RobotViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        reward1Button = findViewById(R.id.reward1)
        reward2Button = findViewById(R.id.reward2)
        reward3Button = findViewById(R.id.reward3)

        reward1Energy = findViewById(R.id.reward1Energy)
        reward2Energy = findViewById(R.id.reward2Energy)
        reward3Energy = findViewById(R.id.reward3Energy)

        // Gets 3 random rewards
        val rewardNames = robotViewModel.rewards.keys.toList().asSequence().shuffled().take(3).toMutableList()
        // sorts the rewards so that we get them in alpha order from left to right
        rewardNames.sort()
        reward1Button.text = rewardNames[0]
        reward2Button.text = rewardNames[1]
        reward3Button.text = rewardNames[2]

        reward1Energy.text = robotViewModel.rewards[rewardNames[0]].toString()
        reward2Energy.text = robotViewModel.rewards[rewardNames[1]].toString()
        reward3Energy.text = robotViewModel.rewards[rewardNames[2]].toString()

        availableEnergy = findViewById(R.id.availableEnergy)

        //robotEnergy = 2 // Hardcoded for now
        robotEnergy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 6)
        availableEnergy.setText(robotEnergy.toString())

        reward1Button.setOnClickListener{view : View ->
            if (makePurchase(rewardNames[0])) {
                reward1Button.isEnabled = false
            }
        }

        reward2Button.setOnClickListener{view : View ->
            if (makePurchase(rewardNames[1])) {
                reward2Button.isEnabled = false
            }
        }
        reward3Button.setOnClickListener{view : View ->
            if (makePurchase(rewardNames[2])) {
                reward3Button.isEnabled = false
            }
        }
    }

    private fun setItemPurchased(robotPurchaseMade : String) {
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

    private fun makePurchase(reward : String) : Boolean{
        val costOfPurchase = robotViewModel.rewards[reward]!!
        if (robotEnergy >= costOfPurchase){
            val s1 = reward
            val s2 = getString(R.string.purchased)
            val s3 = "$s1 $s2"
            robotEnergy -= costOfPurchase
            availableEnergy.text = robotEnergy.toString()
            setItemPurchased(reward)
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
            return true
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
            return false
        }
    }
}