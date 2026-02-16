package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.math.pow

class EmiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emi)

        val principal = findViewById<EditText>(R.id.principal)
        val rate = findViewById<EditText>(R.id.rate)
        val years = findViewById<EditText>(R.id.years)
        val calc = findViewById<Button>(R.id.calc)
        val clear = findViewById<Button>(R.id.clear)
        val emiResult = findViewById<TextView>(R.id.emiResult)
        val intResult = findViewById<TextView>(R.id.intResult)

        calc.setOnClickListener {

            if (principal.text.isEmpty() || rate.text.isEmpty() || years.text.isEmpty()) {
                Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val P = principal.text.toString().toDouble()
            val R = rate.text.toString().toDouble() / 12 / 100
            val N = years.text.toString().toDouble() * 12

            val emi = P * R * (1 + R).pow(N) / ((1 + R).pow(N) - 1)
            val interest = (emi * N) - P

            emiResult.text = "Monthly EMI: ₹ %.2f".format(emi)
            intResult.text = "Total Interest: ₹ %.2f".format(interest)
        }

        clear.setOnClickListener {
            principal.text.clear()
            rate.text.clear()
            years.text.clear()
            emiResult.text = "Monthly EMI:"
            intResult.text = "Total Interest:"
        }
    }
}
