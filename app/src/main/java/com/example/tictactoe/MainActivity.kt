package com.example.tictactoe

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var moveIsX = true
    var gameEnd = false
    val history = Stack<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cleaning board
        for (position in 0..(board.childCount - 1)) {
            val buttonPosition = board.getChildAt(position) as Button
            buttonPosition.text = ""
        }
    }

    fun boardMove(view: View) {
        if (!gameEnd) {
            val button = view as Button

            if (button.text == "") {
                if (moveIsX) {
                    button.text = "X"
                } else {
                    button.text = "O"
                }
                //keeping history
                history.push(button.id)
                moveIsX = !moveIsX

            } else {
                Toast.makeText(this, "You can't move here!", Toast.LENGTH_SHORT).show()
            }

            if (gameWon()) {
                Toast.makeText(this, "Congratulation! You WON XD", Toast.LENGTH_LONG).show()
                gameEnd = true
            }
        } else {
            Toast.makeText(
                this,
                "The game has already ended. If you want to play again press RESTART",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun resetBoard(view: View) {
        for (position in 0..(board.childCount - 1)) {
            val buttonPosition = board.getChildAt(position) as Button
            buttonPosition.text = ""
        }
        gameEnd = false
    }

    fun back(view: View) {
        if (history.isNotEmpty()) {
            findViewById<Button>(history.pop()).text = ""
            gameEnd = false
        }
    }

    private fun gameWon(): Boolean {
        // Winning configurations of buttons in gridLayout
        val wonConfigs = arrayListOf<List<Int>>(
            arrayListOf(0, 1, 2), arrayListOf(3, 4, 5), arrayListOf(6, 7, 8),
            arrayListOf(0, 4, 8), arrayListOf(2, 4, 6), arrayListOf(0, 3, 6),
            arrayListOf(1, 4, 7), arrayListOf(2, 5, 8)
        )


        for (config in wonConfigs) {
            val symbols = mutableSetOf<String>()
            for (index in config) {
                val button = board.getChildAt(index) as Button
                symbols.add(button.text.toString())
            }
            if (symbols.size == 1 && "" !in symbols) {
                return true
            }
        }

        return false
    }
}
