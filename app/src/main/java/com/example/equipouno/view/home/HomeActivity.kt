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
        blinkHandler = Handler(Looper.getMainLooper())
        blinkRunnable = object : Runnable {
            override fun run() {
                blinkingButton.visibility = if (blinkingButton.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
                blinkHandler.postDelayed(this, 500) // Cambia cada 500ms
            }
        }
        blinkHandler.post(blinkRunnable) // Iniciar el parpadeo
    }

    private fun stopBlinkingButton() {
        blinkHandler.removeCallbacks(blinkRunnable) // Detener el parpadeo
        blinkingButton.visibility = View.GONE // Ocultar el botón
    }

    private fun startSpinning() {
        isSpinning = true
        stopBlinkingButton() // Detener el parpadeo
        val spinDuration = Random.nextInt(3000, 5000).toLong() // Duración aleatoria entre 3 y 5 segundos
        val spinAmount = Random.nextFloat() * 720 // Cantidad de giro aleatoria entre 0 y 720 grados

        // Girar la botella
        ObjectAnimator.ofFloat(bottleImage, "rotation", spinDirection, spinDirection + spinAmount).apply {
            duration = spinDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    spinDirection += spinAmount // Actualizar dirección de giro
                    showCountdown() // Mostrar cuenta regresiva
                }
            })
            start()
        }
    }


    private fun showCountdown() {
        val handler = Handler(Looper.getMainLooper())
        var count = 4 // Iniciar cuenta regresiva en 3
        timerText.text = count.toString()
        timerText.visibility = View.VISIBLE // Mostrar texto

        val runnable = object : Runnable {
            override fun run() {
                if (count > 0) {
                    count--
                    timerText.text = count.toString()
                    handler.postDelayed(this, 1000) // Esperar 1 segundo antes de repetir
                } else {
                    timerText.visibility = View.GONE // Ocultar el texto
                    isSpinning = false // Permitir que la botella vuelva a girar
                    blinkingButton.visibility = View.VISIBLE // Mostrar el botón de nuevo
                    startBlinkingButton() // Reiniciar el parpadeo del botón
                }
            }
        }
        handler.post(runnable) // Iniciar la cuenta regresiva
    }
}
