package com.example.guicomponentsapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var size = 24f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val btnSize = findViewById<Button>(R.id.btnSize)
        val btnColor = findViewById<Button>(R.id.btnColor)

        // Change font size
        btnSize.setOnClickListener {
            size += 4
            if (size > 40) size = 20f
            textView.textSize = size
        }

        // Change color
        btnColor.setOnClickListener {
            val colors = arrayOf(
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.MAGENTA
            )

            val randomColor = colors.random()
            textView.setTextColor(randomColor)
        }
    }
}