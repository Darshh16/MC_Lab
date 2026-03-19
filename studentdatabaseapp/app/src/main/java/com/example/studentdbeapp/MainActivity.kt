package com.example.studentdbeapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper
    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<String>
    var studentList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val addBtn = findViewById<Button>(R.id.addBtn)
        listView = findViewById(R.id.listView)

        db = DatabaseHelper(this)

        loadData()

        addBtn.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty()) {
                db.insertStudent(name, age.toInt())
                nameInput.text.clear()
                ageInput.text.clear()
                loadData()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->

            val selected = studentList[position]
            val id = selected.split(" ")[0].toInt()

            AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("YES") { _, _ ->
                    db.deleteStudent(id)
                    loadData()
                }
                .setNegativeButton("NO", null)
                .show()
        }
    }

    private fun loadData() {
        studentList = db.getStudents()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        listView.adapter = adapter
    }
}