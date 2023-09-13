package com.example.hw1_robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView

    private lateinit var rotateCW : ImageButton
    private lateinit var rotateCCW : ImageButton

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

        rotateCW = findViewById(R.id.rot_clockwise)
        rotateCCW = findViewById(R.id.rot_counter)

        rotateCW.setOnClickListener{view : View -> toggleImage(false)}
        rotateCCW.setOnClickListener{view : View -> toggleImage(true)}
    }

    private fun toggleImage(isCCW: Boolean) {
        if(isCCW) {
            turnCount++
            if(turnCount > 3) {
                turnCount = 1
            }
        } else {
            turnCount--
            if(turnCount <= 0) {
                turnCount = 3
            }
        }

        // check to see if this is the first move to ensure the first robot is red
        turnCount = if (isFirstMove()) 1 else turnCount

        setRobotTurn()
        setRobotImages()
    }

    private fun isFirstMove() : Boolean {
        for (robot in robots) {
            if (robot.myTurn) {return false}
        }
        return true
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