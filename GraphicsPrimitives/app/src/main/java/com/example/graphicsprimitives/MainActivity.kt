package com.example.graphicsprimitives

import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var bitmap: Bitmap
    lateinit var canvas: Canvas
    lateinit var paint: Paint
    lateinit var imageView: ImageView

    var lastX = 0f
    var lastY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.canvasView)

        bitmap = Bitmap.createBitmap(1080, 2000, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)

        paint = Paint()
        paint.color = Color.YELLOW
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        imageView.setImageBitmap(bitmap)

        // FINGER DRAWING
        imageView.setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = x
                    lastY = y
                }

                MotionEvent.ACTION_MOVE -> {
                    canvas.drawLine(lastX, lastY, x, y, paint)
                    lastX = x
                    lastY = y
                    imageView.invalidate()
                }
            }
            true
        }

        // SHAPES
        findViewById<Button>(R.id.btnLine).setOnClickListener {
            canvas.drawLine(100f, 200f, 900f, 400f, paint)
            imageView.invalidate()
        }

        findViewById<Button>(R.id.btnRect).setOnClickListener {
            canvas.drawRect(200f, 500f, 900f, 900f, paint)
            imageView.invalidate()
        }

        findViewById<Button>(R.id.btnCircle).setOnClickListener {
            canvas.drawCircle(550f, 1300f, 250f, paint)
            imageView.invalidate()
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            bitmap.eraseColor(Color.TRANSPARENT)
            imageView.invalidate()
        }
    }
}
