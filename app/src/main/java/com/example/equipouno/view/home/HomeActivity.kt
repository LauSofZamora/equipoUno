package com.example.equipouno.view.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.equipouno.R
import com.example.equipouno.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var timerText: TextView
    private lateinit var bottleImage: ImageView
    private var timeLeft = 3 // Tiempo inicial del contador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "onCreate called")
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Conectar los elementos de la UI
        timerText = findViewById(R.id.timerText)
        bottleImage = findViewById(R.id.bottleImage)

        // Iniciar el contador regresivo
        startCountdown()
    }

    private fun startCountdown() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (timeLeft > 0) {
                    timeLeft-- // Disminuir el contador
                    timerText.text = timeLeft.toString() // Actualizar el texto del contador
                    handler.postDelayed(this, 1000L) // Esperar 1 segundo antes de repetir
                }
            }
        }
        handler.post(runnable) // Iniciar el runnable
    }
}
