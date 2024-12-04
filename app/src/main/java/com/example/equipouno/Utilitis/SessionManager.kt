package com.example.equipouno.Utilitis

import android.content.Context

class SessionManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    // Método para guardar el email
    fun saveUserEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        editor.apply()
    }

    // Método para obtener el email
    fun getUserEmail(): String? {
        return sharedPreferences.getString("user_email", null)
    }

    // Método para limpiar la sesión
    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
