package com.example.equipouno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.example.equipouno.model.Reto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetosViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val retosCollection = firestore.collection("retos")

    private val _retos = MutableLiveData<List<Reto>>()
    val retos: LiveData<List<Reto>> get() = _retos

    // Cargar los retos desde Firebase Firestore
    fun cargarRetos() {
        firestore.collection("retos").get()
            .addOnSuccessListener { result ->
                val listaRetos = result.documents.map { doc ->
                    val descripcion = doc.getString("descripcion") ?: "Sin descripción"
                    Reto(
                        id = doc.id,
                        descripcion = descripcion
                    )
                }
                // Asegúrate de agregar el nuevo reto al principio
                _retos.value = listaRetos.reversed()  // Invertir la lista para que los nuevos retos estén al principio
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                _retos.value = emptyList()
            }
    }


    // Agregar un nuevo reto a Firestore
    fun agregarReto(descripcion: String) {
        viewModelScope.launch {
            val nuevoReto = hashMapOf("descripcion" to descripcion)
            retosCollection.add(nuevoReto)
                .addOnSuccessListener { documentReference ->
                    // Crear un objeto Reto y agregarlo al inicio de la lista
                    val reto = Reto(id = documentReference.id, descripcion = descripcion)
                    val updatedList = listOf(reto) + _retos.value.orEmpty()  // Agregar al inicio
                    _retos.value = updatedList
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }


    // Editar un reto existente
    fun editarReto(reto: Reto, nuevaDescripcion: String) {
        viewModelScope.launch {
            retosCollection.document(reto.id)
                .update("descripcion", nuevaDescripcion) // Actualiza el campo "descripcion"
                .addOnSuccessListener {
                    cargarRetos() // Recargar la lista de retos
                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Maneja cualquier error
                }
        }
    }

    // Eliminar un reto
    fun eliminarReto(reto: Reto) {
        viewModelScope.launch {
            retosCollection.document(reto.id).delete() // Eliminar el reto de la base de datos
                .addOnSuccessListener {
                    // Eliminar el reto de la lista local
                    _retos.value = _retos.value?.filter { it.id != reto.id }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Manejar cualquier error
                }
        }
    }

}
