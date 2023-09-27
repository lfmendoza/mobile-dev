package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup

data class GameSttings (val playerOneName: String = "", val playerTwoName: String = "", val boardSize: Int)

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerOneNameEditText = findViewById<EditText>(R.id.editTextPlayerName1)
        val playerTwoNameEditText = findViewById<EditText>(R.id.editTextPlayerName2)
        val boardSizeRadioGroup = findViewById<RadioGroup>(R.id.radioGroupBoardSize)
        val startGameButton = findViewById<Button>(R.id.buttonStartGame)

        val playerOneName = playerOneNameEditText.text.toString()
        val playerTwoName = playerTwoNameEditText.text.toString()
        val boardSize = when (boardSizeRadioGroup.checkedRadioButtonId) {
            R.id.radioButton3x3 -> 3
            R.id.radioButton4x4 -> 4
            R.id.radioButton5x5 -> 5
            else -> 3
        }

        startGameButton.setOnClickListener{
            InitGame(GameSttings(playerOneName, playerTwoName, boardSize))
        }
    }

    private fun InitGame(gameSettings: GameSttings) {
        val intent = Intent(this@MainActivity, TicTacToe::class.java).apply {
            putExtra(TicTacToe.EXTRA_PLAYER_ONE_NAME, gameSettings.playerOneName)
            putExtra(TicTacToe.EXTRA_PLAYER_TWO_NAME, gameSettings.playerTwoName)
            putExtra(TicTacToe.EXTRA_BOARD_SIZE, gameSettings.boardSize)
        }
        startActivity(intent)
    }
}
