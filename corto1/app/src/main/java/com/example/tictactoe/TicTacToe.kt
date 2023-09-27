

package com.example.tictactoe

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TicTacToe : AppCompatActivity() {
    companion object {
        const val EXTRA_PLAYER_ONE_NAME = "player_1"
        const val EXTRA_PLAYER_TWO_NAME = "player_2"
        const val EXTRA_BOARD_SIZE = "board_size"
    }

    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        gridLayout = findViewById(R.id.gridLayout)

        val playerOneName = intent.getStringExtra(EXTRA_PLAYER_ONE_NAME)
        val playerTwoName = intent.getStringExtra(EXTRA_PLAYER_TWO_NAME)
        val boardSize = intent.getIntExtra(EXTRA_BOARD_SIZE, 3)
        val currentTurn = findViewById<TextView>(R.id.textViewCurrentTurn)
        var playerOneturn: Boolean = true

        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                val cellButton = Button(this)
                cellButton.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(row)
                    columnSpec = GridLayout.spec(col)
                    width = GridLayout.LayoutParams.WRAP_CONTENT
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    setMargins(8, 8, 8, 8)
                    setGravity(Gravity.CENTER)
                }
                cellButton.setOnClickListener {
                    if(playerOneturn)
                        currentTurn.text = "Current turn: $playerOneName"
                    else
                        currentTurn.text = "Current turn: $playerTwoName"

                    playerOneturn = !playerOneturn

                }
                gridLayout.addView(cellButton)
            }
        }
    }
}