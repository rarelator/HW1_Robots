package com.example.hw1_robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView

    private lateinit var robotImages : MutableList<ImageView>

    private var turnCount = 0
    private val robots = listOf( // immutable
        Robot(R.string.red_robot_msg, false,
            R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small),
        Robot(R.string.white_robot_msg, false,
            R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small),
        Robot(R.string.yellow_robot_msg, false,
            R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redImg = findViewById(R.id.redRobot)
        whiteImg = findViewById(R.id.whiteRobot)
        yellowImg = findViewById(R.id.yellowRobot)

        robotImages = mutableListOf(redImg, whiteImg, yellowImg)

        redImg.setOnClickListener{view : View -> toggleImage()}
        whiteImg.setOnClickListener{view : View -> toggleImage()}
        yellowImg.setOnClickListener{view : View -> toggleImage()}
    }

    private fun toggleImage() {
        turnCount++
        if(turnCount > 3) {
            turnCount = 1
        }

        setRobotTurn()
        setRobotImages()
    }

    private fun setRobotTurn() {
        for(robot in robots) {robot.myTurn = false}
        robots[turnCount - 1].myTurn = true
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