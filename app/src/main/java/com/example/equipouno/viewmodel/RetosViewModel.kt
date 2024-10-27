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

    // Agregar un nuevo reto a la base de datos
    fun agregarReto(descripcion: String) {
        viewModelScope.launch {
            val nuevoReto = Reto(descripcion = descripcion)
            dbHelper.insertReto(nuevoReto) // Inserta en la base de datos
            _retos.postValue(dbHelper.obtenerRetos()) // Actualiza la lista de retos
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
            dbHelper.deleteReto(reto)
            _retos.postValue(dbHelper.obtenerRetos())
        }
    }
}