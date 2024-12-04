package com.example.equipouno.view.instrucciones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.equipouno.databinding.ActivityInstruccionesBinding
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        // Inicializa y controla la animación
        val lottieAnimationView: LottieAnimationView = binding.lottieAnimationView
        lottieAnimationView.setAnimation("celebration.json") // Archivo JSON desde assets
        lottieAnimationView.playAnimation() // Inicia la animación
        lottieAnimationView.loop(true) // Repetir la animación
    }
}