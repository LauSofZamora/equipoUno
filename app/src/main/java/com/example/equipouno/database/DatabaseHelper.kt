package com.example.equipouno.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.equipouno.R
import com.example.equipouno.model.Reto

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "retos.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE retos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descripcion TEXT NOT NULL
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS retos")
        onCreate(db)
    }

    fun insertReto(reto: Reto) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("descripcion", reto.descripcion)
        }
        db.insert("Retos", null, values)
    }

    fun obtenerRetos(): List<Reto> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Retos", null)
        val listaRetos = mutableListOf<Reto>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            listaRetos.add(Reto(id, descripcion))
        }
        cursor.close()
        return listaRetos
    }

    fun updateReto(reto: Reto): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("descripcion", reto.descripcion)
        }
        return db.update(
            "Retos",
            values,
            "id = ?",
            arrayOf(reto.id.toString())
        ) > 0
    }

    fun deleteReto(reto: Reto): Boolean {
        val db = this.writableDatabase
        return db.delete(
            "Retos",
            "id = ?",
            arrayOf(reto.id.toString())
        ) > 0
    }
}