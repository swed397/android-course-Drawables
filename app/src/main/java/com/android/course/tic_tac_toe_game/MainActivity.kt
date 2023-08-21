package com.android.course.tic_tac_toe_game

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children


class MainActivity : AppCompatActivity() {
    private lateinit var field: GridLayout
    private lateinit var imageViewFieldList: List<ImageView>

    private lateinit var ticTacToeGame: TicTacToeGame

    private lateinit var textViewAiCounter: TextView
    private lateinit var textViewHumanCounter: TextView

    private lateinit var starImage: ImageView
    private lateinit var wineImage: ImageView

    private lateinit var button: Button

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setComponents()
        setClickAction()
        ticTacToeGame = TicTacToeGame(imageViewFieldList)

        button.setOnClickListener { newGame() }
    }

    private fun setComponents() {
        field = findViewById(R.id.field)
        imageViewFieldList = field.children.map { it as ImageView }.toList()

        button = findViewById(R.id.button)

        textViewAiCounter = findViewById(R.id.ai_counter)
        textViewHumanCounter = findViewById(R.id.human_counetr)

        starImage = findViewById(R.id.star_image)
        wineImage = findViewById(R.id.wine_image)
    }

    private fun setClickAction() {
        imageViewFieldList
            .forEachIndexed { index, imageView ->
                imageView.setOnClickListener {
                    if (imageView.drawable.level == IMAGE_LEVEL_EMPTY ||
                        imageView.drawable.level == IMAGE_LEVEL_NULl
                    ) {
                        imageView.setImageLevel(IMAGE_LEVEL_X)
                        ticTacToeGame.humanClick(index)
                        ticTacToeGame.aiTurnClick()
                        ticTacToeGame.checkWinner()
                        counter++
                        if (counter > MIN_CLICKS_TO_WIN && ticTacToeGame.winner != null) {
                            winnerViewHandler(ticTacToeGame.winner)
                        }

                        if (counter >= MAX_CLICKS_TO_WIN && ticTacToeGame.winner == null) {
                            ticTacToeGame.winner = Winner.FRIEND
                            winnerViewHandler(ticTacToeGame.winner)
                        }
                    }
                }
            }
    }

    private fun winnerViewHandler(winner: Winner?) {
        when (winner) {
            Winner.HUMAN -> {
                Toast.makeText(this, winner.text, Toast.LENGTH_LONG).show()
                textViewHumanCounter.text =
                    (Integer.valueOf(textViewHumanCounter.text.toString()) + 1).toString()
                starImage.setImageLevel(starImage.drawable.level + IMAGE_VIEW_PAINT)
            }

            Winner.AI -> {
                Toast.makeText(this, winner.text, Toast.LENGTH_LONG).show()
                textViewAiCounter.text =
                    (Integer.valueOf(textViewAiCounter.text.toString()) + 1).toString()
                wineImage.setImageLevel(wineImage.drawable.level + IMAGE_VIEW_PAINT)
            }

            Winner.FRIEND -> {
                Toast.makeText(this, winner.text, Toast.LENGTH_LONG).show()
                textViewHumanCounter.text =
                    (Integer.valueOf(textViewHumanCounter.text.toString()) + 1).toString()
                textViewAiCounter.text =
                    (Integer.valueOf(textViewAiCounter.text.toString()) + 1).toString()
                starImage.setImageLevel(starImage.drawable.level + IMAGE_VIEW_PAINT)
                wineImage.setImageLevel(wineImage.drawable.level + IMAGE_VIEW_PAINT)
            }

            else -> {
                throw Exception("Что-то пошло не так")
            }
        }

        resetSettings()

        val humanWins = Integer.valueOf(textViewHumanCounter.text.toString())
        val aiWins = Integer.valueOf(textViewAiCounter.text.toString())
        if (humanWins == MAX_WINS || aiWins == MAX_WINS) {
            createDialog(this, humanWins, aiWins)
        }
    }

    private fun resetSettings() {
        ticTacToeGame.resetGame()
        counter = 0
    }

    private fun newGame() {
        resetSettings()
        textViewAiCounter.text = 0.toString()
        textViewHumanCounter.text = 0.toString()
        starImage.setImageLevel(IMAGE_LEVEL_NULl)
        wineImage.setImageLevel(IMAGE_LEVEL_NULl)
    }

    private fun createDialog(activity: Activity?, humanWins: Int, aiWins: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Конец игры!")
            .setMessage(
                "Победа за ${
                    if (humanWins > aiWins) "Человеком"
                    else if (humanWins == aiWins) "Дружбой"
                    else "Искуственным интелектом"
                } "
            )
            .setPositiveButton(
                "New game"
            ) { dialog, id ->
                resetSettings()
                newGame()
            }
        builder.create().show()
    }

    companion object {
        const val MAX_WINS = 3
        const val MIN_CLICKS_TO_WIN = 2
        const val MAX_CLICKS_TO_WIN = 5
        const val IMAGE_LEVEL_NULl = 0
        const val IMAGE_LEVEL_EMPTY = 1
        const val IMAGE_LEVEL_X = 2
        const val IMAGE_VIEW_PAINT = 3000
    }
}