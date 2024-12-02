//package com.example.equipouno.database
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.example.equipouno.R
//import com.example.equipouno.model.Reto
//
//class DatabaseHelper(context: Context) :
//    SQLiteOpenHelper(context, "retos.db", null, 1) {
//
//    companion object {
//
//        private const val TABLE_NAME = "Retos"
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {
//        val createTable = """
//            CREATE TABLE retos (
//                id INTEGER PRIMARY KEY AUTOINCREMENT,
//                descripcion TEXT NOT NULL
//            )
//        """
//        db.execSQL(createTable)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS retos")
//        onCreate(db)
//    }
//
//    fun insertReto(reto: Reto) {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put("descripcion", reto.descripcion)
//        }
//        db.insert("Retos", null, values)
//    }
//
//    fun obtenerRetos(): List<Reto> {
//        val db = readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM Retos", null)
//        val listaRetos = mutableListOf<Reto>()
//
//        while (cursor.moveToNext()) {
//            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
//            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
//            listaRetos.add(Reto(id, descripcion))
//        }
//        cursor.close()
//        return listaRetos
//    }
//
//    fun updateReto(reto: Reto): Boolean {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//            put("descripcion", reto.descripcion)
//        }
//        return db.update(
//            "Retos",
//            values,
//            "id = ?",
//            arrayOf(reto.id.toString())
//        ) > 0
//    }
//
//    fun deleteReto(reto: Reto): Boolean {
//        val db = this.writableDatabase
//        return db.delete(
//            "Retos",
//            "id = ?",
//            arrayOf(reto.id.toString())
//        ) > 0
//    }
//
//    fun getRandomReto(): String {
//        val db = this.readableDatabase
//        var descripcion = "No hay retos disponibles"
//
//        try {
//            // Primero, verificamos cuántos retos hay
//            val countCursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
//            if (countCursor.moveToFirst()) {
//                val count = countCursor.getInt(0)
//                if (count > 0) {
//                    // Si hay retos, seleccionamos uno aleatorio
//                    val randomIndex = kotlin.random.Random.nextInt(count)
//                    val cursor = db.rawQuery("""
//                        SELECT descripcion FROM $TABLE_NAME
//                        LIMIT 1 OFFSET $randomIndex
//                    """.trimIndent(), null)
//
//                    if (cursor.moveToFirst()) {
//                        val descripcionIndex = cursor.getColumnIndex("descripcion")
//                        if (descripcionIndex != -1) {
//                            descripcion = cursor.getString(descripcionIndex)
//                        }
//                    }
//                    cursor.close()
//                }
//            }
//            countCursor.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return descripcion
//    }
//}