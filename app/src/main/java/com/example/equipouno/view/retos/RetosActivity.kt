package com.example.equipouno.view.retos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipouno.databinding.ActivityRetosBinding // Importa el binding generado


class RetosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetosBinding // Definimos el binding
    private lateinit var retosAdapter: RetosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el binding
        binding = ActivityRetosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar personalizada
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
            // Restablecer el audio si está en ON
            // restablecerAudio()
        }

        // Configurar RecyclerView
        retosAdapter = RetosAdapter()
        binding.recyclerViewRetos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRetos.adapter = retosAdapter

        // Configurar FloatingActionButton
        binding.fabAddReto.setOnClickListener {
            // Lanza cuadro de diálogo para agregar reto
            // mostrarCuadroDialogoAgregarReto()
        }
    }
}

