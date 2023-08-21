package com.android.course.tic_tac_toe_game

import android.widget.ImageView

class TicTacToeGame(
    private val field: List<ImageView>,
) {

    var winner: Winner? = null

    private var counter = 0

    private val gameState = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    private val winPositions = arrayOf(
        arrayOf(0, 1, 2),
        arrayOf(3, 4, 5),
        arrayOf(6, 7, 8),
        arrayOf(0, 3, 6),
        arrayOf(1, 4, 7),
        arrayOf(2, 5, 8),
        arrayOf(0, 4, 8),
        arrayOf(2, 4, 6)
    )

    fun humanClick(index: Int) {
        gameState[index] = X
    }

    fun aiTurnClick() {
        val randomIndex = (field.indices).random()
        val imageView = field[randomIndex]

        if (imageView.drawable.level == MainActivity.IMAGE_LEVEL_NULl ||
            imageView.drawable.level == MainActivity.IMAGE_LEVEL_EMPTY
        ) {
            imageView.setImageLevel(IMAGE_LEVEL_O)
            gameState[randomIndex] = O
            checkWinner()
            counter++
        } else {
            if (counter < MAX_TURNS_AI_WIN) aiTurnClick() else return
        }
    }

    fun checkWinner() {
        for (winPosition in winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                gameState[winPosition[1]] == gameState[winPosition[2]] &&
                gameState[winPosition[0]] != NULL_STATE
            ) {
                winner = if (gameState[winPosition[0]] == X) {
                    Winner.HUMAN
                } else {
                    Winner.AI
                }
            }
        }
    }

    fun resetGame() {
        winner = null
        gameState.fill(NULL_STATE)
        counter = NULL_STATE
        field.forEach { it.setImageLevel(1) }
    }

    companion object {
        const val NULL_STATE = 0
        const val X = 1
        const val O = 2
        const val MAX_TURNS_AI_WIN = 4
        const val IMAGE_LEVEL_O = 3
    }
}
