package com.example.equipouno.view.instrucciones


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.equipouno.R
import com.example.equipouno.databinding.ActivityInstruccionesBinding

class InstruccionesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstruccionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstruccionesBinding.inflate(layoutInflater)
        setContentView(binding.root) // Usa binding.root para establecer el layout

        setSupportActionBar(binding.toolbar3)
        supportActionBar?.title = "" // Elimina el título de la Toolbar

        // Define la acción al presionar el botón "Atrás"
        binding.btnBack.setOnClickListener {
            onBackPressed() // Llama a onBackPressed() para regresar a la actividad anterior
        }
    }
}