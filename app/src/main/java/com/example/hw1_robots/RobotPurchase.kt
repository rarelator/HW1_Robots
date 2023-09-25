package com.example.hw1_robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

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

        robotEnergy = 2
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

    private fun makePurchase(costOfPurchase : Int){
        if (robotEnergy >= costOfPurchase){
            val s1 = getString(R.string.reward_C_text)
            val s2 = getString(R.string.purchased)
            val s3 = s1 + " " + s2
            robotEnergy -= costOfPurchase
            availableEnergy.setText(robotEnergy.toString())
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}