package com.example.equipouno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipouno.database.DatabaseHelper
import com.example.equipouno.model.Reto
import kotlinx.coroutines.launch

class RetosViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val _retos = MutableLiveData<List<Reto>>()
    val retos: LiveData<List<Reto>> get() = _retos

    init {
        cargarRetos()
    }

    // Cargar los retos desde la base de datos al iniciar el ViewModel
    private fun cargarRetos() {
        _retos.value = dbHelper.obtenerRetos()
    }

    // Agregar un nuevo reto a la base de datos y colocarlo al inicio de la lista
    fun agregarReto(descripcion: String) {
        viewModelScope.launch {
            val nuevoReto = Reto(descripcion = descripcion)
            dbHelper.insertReto(nuevoReto) // Inserta en la base de datos

            // Obtén la lista actual de retos
            val listaActual = _retos.value?.toMutableList() ?: mutableListOf()
            listaActual.add(0, nuevoReto) // Agrega el nuevo reto al inicio
            _retos.postValue(listaActual) // Actualiza la lista de retos
        }
    }

    fun editarReto(reto: Reto, nuevaDescripcion: String) {
        viewModelScope.launch {
            val retoActualizado = reto.copy(descripcion = nuevaDescripcion)
            dbHelper.updateReto(retoActualizado)
            _retos.postValue(dbHelper.obtenerRetos())
        }
    }

    fun eliminarReto(reto: Reto) {
        viewModelScope.launch {
            // Eliminar el reto de la base de datos
            dbHelper.deleteReto(reto)

            // Actualizar la lista de retos en _retos
            val listaActual = _retos.value?.toMutableList()
            listaActual?.remove(reto) // Eliminar el reto de la lista
            _retos.postValue(listaActual) // Publicar la lista actualizada
        }
    }

}
