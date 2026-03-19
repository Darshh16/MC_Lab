package com.example.studentdbeapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "StudentDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE students(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    fun insertStudent(name: String, age: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("age", age)
        db.insert("students", null, values)
    }

    fun getStudents(): ArrayList<String> {
        val list = ArrayList<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val age = cursor.getInt(2)
            list.add("$id - $name ($age)")
        }

        cursor.close()
        return list
    }

    fun deleteStudent(id: Int) {
        val db = writableDatabase
        db.delete("students", "id=?", arrayOf(id.toString()))
    }
}