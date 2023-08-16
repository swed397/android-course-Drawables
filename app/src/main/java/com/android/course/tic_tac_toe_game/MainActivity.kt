package com.android.course.tic_tac_toe_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    lateinit var textViewAiCounter: TextView
    lateinit var textViewHumanCounter: TextView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val field = findViewById<GridLayout>(R.id.field)
        button = findViewById(R.id.button)


        button.setOnClickListener {
            field.children.map { it as ImageView }.forEach { it.setImageLevel(3) }
        }
    }
}