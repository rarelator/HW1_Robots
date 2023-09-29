
package com.example.hw1_robots

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels

private const val TAG = "MainActivity"
private const val ROBOT_PURCHASES = "com.example.hw1_robots.current_robot_purchases"
class MainActivity : AppCompatActivity() {
    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView
    private lateinit var messageBox : TextView
    private lateinit var reward_button : Button

    private lateinit var robotImages : MutableList<ImageView>

    //    private var turnCount = 1
    private val robots = listOf( // immutable
        Robot(R.string.red_robot_msg, false,
            R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small, 0, 0),
        Robot(R.string.white_robot_msg, false,
            R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small, 0, 0),
        Robot(R.string.yellow_robot_msg, false,
            R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small, 0, 0)
    )

    private val robotViewModel: RobotViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redImg = findViewById(R.id.redRobot)
        whiteImg = findViewById(R.id.whiteRobot)
        yellowImg = findViewById(R.id.yellowRobot)
        messageBox = findViewById(R.id.message_box)
        reward_button = findViewById(R.id.purchase_reward_button)

        robotImages = mutableListOf(redImg, whiteImg, yellowImg)

        if (robotViewModel.getTurnCount() > 0) {
            updateMessageBox()
            setRobotTurn()
            setRobotImages()
        }

        redImg.setOnClickListener{view : View -> toggleImage()}
        whiteImg.setOnClickListener{view : View -> toggleImage()}
        yellowImg.setOnClickListener{view : View -> toggleImage()}
        reward_button.setOnClickListener{view : View ->
            // Toast.makeText(this, "Going to make a purchase", Toast.LENGTH_SHORT).show()
            // passing in the activity that is making the call
            // val intent = Intent(this, RobotPurchase::class.java)
            // intent.putExtra(EXTRA_ROBOT_ENERGY, robots[robotViewModel.getTurnCount() - 1].myEnergy)
            val intent = RobotPurchase.newIntent(this, robots[robotViewModel.getTurnCount() - 1].myEnergy)
            startActivity(intent)
        }

        Log.d(TAG, "onCreate() entered")
        Log.d(TAG, "Instance of viewModel created $robotViewModel")
    }

    companion object {
        fun newIntent(packageContext: Context, numPurchases : Int) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(ROBOT_PURCHASES, numPurchases)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() entered")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() entered")

        if (robotViewModel.getTurnCount() > 0) {
            robots[robotViewModel.getTurnCount() - 1].numOfPurchases = intent.getIntExtra(
                ROBOT_PURCHASES, -1
            )
            Log.d(TAG, robots[robotViewModel.getTurnCount() - 1].numOfPurchases.toString())
            if (robots[robotViewModel.getTurnCount() - 1].numOfPurchases > 0) {
                Toast.makeText(this, "Robot made a purchase", Toast.LENGTH_SHORT).show()
            }
        }
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
        robotViewModel.advanceTurn()
        updateMessageBox()
        setRobotTurn()
        setRobotImages()
    }

    private fun updateMessageBox() {
        messageBox.setText(robots[robotViewModel.getTurnCount() - 1].messageResource)
    }

    private fun setRobotTurn() {
        for(robot in robots) {robot.myTurn = false}
        robots[robotViewModel.getTurnCount() - 1].myTurn = true
        robots[robotViewModel.getTurnCount() - 1].myEnergy += 1
    }

    private fun setRobotImages() {
        for(i in robots.indices) {
            if(robots[i].myTurn) {
                robotImages[i].setImageResource(robots[i].largeImgRes)
            } else {
                robotImages[i].setImageResource(robots[i].smallImgRes)
            }
        }
    }
}