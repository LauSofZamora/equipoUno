package com.example.equipouno.view.retos

import RetosAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipouno.R
import com.example.equipouno.database.DatabaseHelper
import com.example.equipouno.databinding.ActivityRetosBinding
import com.example.equipouno.model.Reto
import com.example.equipouno.viewmodel.RetosViewModel
import com.example.equipouno.viewmodel.RetosViewModelFactory
import android.content.res.ColorStateList
import android.graphics.Color


class RetosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetosBinding
    private lateinit var retosAdapter: RetosAdapter

    private val viewModel: RetosViewModel by viewModels {
        RetosViewModelFactory(DatabaseHelper(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        setupRecyclerView()

        viewModel.retos.observe(this) { retos ->
            retosAdapter.submitList(retos)
        }

        binding.fabAddReto.setOnClickListener {
            mostrarCuadroDialogoAgregarReto()
        }
    }

    private fun setupRecyclerView() {
        retosAdapter = RetosAdapter(
            viewModel = viewModel,
            onEditClick = { reto ->
                mostrarCuadroDialogoEditarReto(reto)
            }
        )

        binding.recyclerViewRetos.apply {
            layoutManager = LinearLayoutManager(this@RetosActivity) // Layout en lista vertical
            adapter = retosAdapter
            setHasFixedSize(true) // Mejora el rendimiento si la lista tiene tamaño fijo
        }
    }

    private fun mostrarCuadroDialogoEditarReto(reto: Reto) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_editar_reto, null, false)

        val etDescripcionReto = dialogView.findViewById<EditText>(R.id.et_descripcion_reto)
        val btnGuardarReto = dialogView.findViewById<Button>(R.id.btn_guardar_reto)
        val btnCancelarReto = dialogView.findViewById<Button>(R.id.btn_cancelar_reto)

        // Establecer la descripción actual del reto
        etDescripcionReto.setText(reto.descripcion)


        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Configurar el botón Guardar inicialmente (ya que hay texto)
        btnGuardarReto.isEnabled = true
        btnGuardarReto.setTextColor(resources.getColor(android.R.color.holo_orange_dark))
        btnGuardarReto.backgroundTintList = ColorStateList.valueOf(Color.WHITE)

        // Monitorear cambios en el texto
        etDescripcionReto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isNotEmpty = !s.isNullOrEmpty()
                btnGuardarReto.isEnabled = isNotEmpty
                if (isNotEmpty) {
                    btnGuardarReto.setTextColor(resources.getColor(android.R.color.holo_orange_dark))
                    btnGuardarReto.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                } else {
                    btnGuardarReto.setTextColor(Color.GRAY)
                    btnGuardarReto.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Configurar el botón Cancelar
        btnCancelarReto.backgroundTintList = ColorStateList.valueOf(resources.getColor(android.R.color.holo_orange_dark))
        btnCancelarReto.setTextColor(Color.WHITE)

        btnCancelarReto.setOnClickListener {
            dialog.dismiss()
        }

        btnGuardarReto.setOnClickListener {
            val nuevaDescripcion = etDescripcionReto.text.toString().trim()
            if (nuevaDescripcion.isNotEmpty()) {
                viewModel.editarReto(reto, nuevaDescripcion)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun mostrarCuadroDialogoAgregarReto() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_reto, null, false)

        val etDescripcionReto = dialogView.findViewById<EditText>(R.id.et_descripcion_reto)
        val btnGuardarReto = dialogView.findViewById<Button>(R.id.btn_guardar_reto)
        val btnCancelarReto = dialogView.findViewById<Button>(R.id.btn_cancelar_reto)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false) // Evita que se cierre al tocar fuera del diálogo
            .create()

        // Colores para los estados del botón Guardar
        val colorDeshabilitado = ColorStateList.valueOf(Color.GRAY)
        val colorHabilitado = ColorStateList.valueOf(resources.getColor(android.R.color.holo_orange_dark))

        // Inicialmente, el botón está deshabilitado y en color gris
        btnGuardarReto.backgroundTintList = colorDeshabilitado

        // Habilitar o deshabilitar el botón según el contenido del EditText
        etDescripcionReto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isNotEmpty = !s.isNullOrEmpty()
                btnGuardarReto.isEnabled = isNotEmpty
                btnGuardarReto.backgroundTintList = if (isNotEmpty) colorHabilitado else colorDeshabilitado
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Cerrar el cuadro de diálogo con el botón Cancelar
        btnCancelarReto.setOnClickListener {
            dialog.dismiss()
        }

        // Guardar el reto al presionar Guardar y actualizar la lista
        btnGuardarReto.setOnClickListener {
            val descripcion = etDescripcionReto.text.toString().trim()
            if (descripcion.isNotEmpty()) {
                viewModel.agregarReto(descripcion) // Guardar en la base de datos
                dialog.dismiss() // Cerrar el cuadro de diálogo
            }
        }

        dialog.show()
    }

}
