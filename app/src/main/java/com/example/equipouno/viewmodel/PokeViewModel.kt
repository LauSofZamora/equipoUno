package com.example.equipouno.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.equipouno.ServicesWeb.ApiUtils
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class PokeViewModel {
    private val apiService = ApiUtils.getApiService()

    suspend fun fetchPokemons(): String {
        return withContext(Dispatchers.IO) {
            // Supongamos que tienes un Retrofit service configurado
            try {
                val response = apiService.getPokemons() // Llamada suspendida
                if (response.isSuccessful) {
                    // Si la respuesta es exitosa, devuelve la URL de la primera imagen
                    val pokemon = response.body()?.pokemonList?.randomOrNull()
                    pokemon?.imagenUrl ?: "No image found" // Devolver un valor predeterminado si no hay imagen
                } else {
                    Log.e("Error", "Error al obtener datos del Pokémon")
                    "Error en la respuesta"
                }
            } catch (e: Exception) {
                Log.e("Error", "Error de red: ${e.message}")
                "Network error"
            }
        }
    }

}