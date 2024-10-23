package com.example.equipouno.view.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.equipouno.R
import kotlin.random.Random
import com.example.equipouno.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var timerText: TextView
    private lateinit var bottleImage: ImageView
    private lateinit var blinkingButton: Button
    private var isSpinning = false
    private var spinDirection = 0f
    private lateinit var blinkHandler: Handler
    private lateinit var blinkRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Conectar los elementos de la UI
        timerText = findViewById(R.id.timerText)
        bottleImage = findViewById(R.id.bottleImage)
        blinkingButton = findViewById(R.id.blinkingButton)

        startBlinkingButton() // Iniciar el parpadeo del botón

        blinkingButton.setOnClickListener {
            if (!isSpinning) {
                startSpinning()
            }
        }
    }

    private fun startBlinkingButton() {
        val fadeOut = ObjectAnimator.ofFloat(blinkingButton, "alpha", 1f, 0.5f).apply {
            duration = 700 // Duración de cada fase de la animación (medio segundo)
        }

        val fadeIn = ObjectAnimator.ofFloat(blinkingButton, "alpha", 0.5f, 1f).apply {
            duration = 700 // Igual duración para que la transición sea suave
        }

        // Ejecutar las animaciones en bucle
        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (blinkingButton.visibility == View.VISIBLE) {
                    fadeIn.start() // Iniciar fadeIn solo si el botón está visible
                }
            }
        })

        fadeIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (blinkingButton.visibility == View.VISIBLE) {
                    fadeOut.start() // Iniciar fadeOut solo si el botón está visible
                }
            }
        })

        fadeOut.start() // Iniciar el ciclo con fadeOut
    }

    private fun startSpinning() {
        isSpinning = true
        blinkingButton.clearAnimation() // Detener cualquier animación en curso
        blinkingButton.visibility = View.GONE // Ocultar instantáneamente

        val spinDuration = Random.nextInt(3000, 5000).toLong() // Duración aleatoria
        val spinAmount = Random.nextFloat() * 2080 + 1200 // Giro entre 360 y 1080 grados

        ObjectAnimator.ofFloat(bottleImage, "rotation", spinDirection, spinDirection + spinAmount).apply {
            duration = spinDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    spinDirection = (spinDirection + spinAmount) % 360 // Actualizar dirección
                    showCountdown() // Mostrar cuenta regresiva
                }
            })
            start()
        }
    }

    private fun showCountdown() {
        val handler = Handler(Looper.getMainLooper())
        var count = 4

        timerText.text = count.toString()
        timerText.visibility = View.VISIBLE

        val runnable = object : Runnable {
            override fun run() {
                if (count > 0) {
                    count--
                    timerText.text = count.toString()
                    handler.postDelayed(this, 1000)
                } else {
                    timerText.visibility = View.GONE // Ocultar el texto
                    blinkingButton.visibility = View.VISIBLE // Mostrar el botón de nuevo
                    startBlinkingButton() // Reiniciar el parpadeo
                    isSpinning = false // Permitir otro giro
                }
            }
        }
        handler.post(runnable)
    }
}
