package com.example.equipouno.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    fun getRandomReto(callback: (String) -> Unit) {
        val retosCollection = FirebaseFirestore.getInstance().collection("retos")
        retosCollection.get()
            .addOnSuccessListener { result ->
                val retos = result.documents.mapNotNull { it.getString("descripcion") }
                if (retos.isNotEmpty()) {
                    val randomReto = retos.random() // Selecciona un reto aleatorio
                    callback(randomReto)
                } else {
                    callback("No hay retos disponibles")
                }
            }
            .addOnFailureListener {
                callback("Error al obtener retos")
            }
    }
}
