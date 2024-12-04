package com.example.equipouno.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.equipouno.ServicesWeb.ApiUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor() : ViewModel() {

    private val apiService = ApiUtils.getApiService()

    suspend fun fetchPokemons(): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPokemons()
                if (response.isSuccessful) {
                    val pokemon = response.body()?.pokemonList?.randomOrNull()
                    Log.d("Pokemon Info", "Pokemon: $pokemon")  // Verifica si el Pokémon es null o no
                    pokemon?.img ?: "No image found"
                } else {
                    Log.e("Error", "Error al obtener el Pokémon")
                    "Error en la respuesta"
                }
            } catch (e: Exception) {
                Log.e("Error", "Error de red: ${e.message}")
                "Network error"
            }
        }
    }
}