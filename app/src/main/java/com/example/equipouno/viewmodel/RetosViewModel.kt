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

                _retos.value = listaRetos.reversed()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                _retos.value = emptyList()
            }
    }


    fun agregarReto(descripcion: String) {
        viewModelScope.launch {
            val nuevoReto = hashMapOf("descripcion" to descripcion)
            retosCollection.add(nuevoReto)
                .addOnSuccessListener { documentReference ->
                    val reto = Reto(id = documentReference.id, descripcion = descripcion)
                    val updatedList = listOf(reto) + _retos.value.orEmpty()
                    _retos.value = updatedList
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }


    fun editarReto(reto: Reto, nuevaDescripcion: String) {
        viewModelScope.launch {
            retosCollection.document(reto.id)
                .update("descripcion", nuevaDescripcion)
                .addOnSuccessListener {
                    cargarRetos()
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    fun eliminarReto(reto: Reto) {
        viewModelScope.launch {
            retosCollection.document(reto.id).delete()
                .addOnSuccessListener {
                    _retos.value = _retos.value?.filter { it.id != reto.id }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

}
