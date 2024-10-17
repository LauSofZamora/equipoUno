package com.example.equipouno

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.equipouno.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Referenciar el LottieAnimationView (botella animada)
        val lottieBottle: LottieAnimationView = findViewById(R.id.lottieBottle)

        // Opcional: Configurar propiedades dinámicas de la animación
        lottieBottle.setAnimation("bottle_animation.json")
        lottieBottle.playAnimation()

    }
}
