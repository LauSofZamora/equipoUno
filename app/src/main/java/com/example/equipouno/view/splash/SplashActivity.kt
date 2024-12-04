package com.example.equipouno.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.equipouno.R
import com.example.equipouno.view.home.HomeActivity
import com.example.equipouno.view.login.LoginActivity
import com.example.equipouno.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        hideSystemUI()

        val lottieBottle: LottieAnimationView = findViewById(R.id.lottieBottle)
        lottieBottle.setAnimation("bottle_animation.json")
        lottieBottle.playAnimation()

        viewModel.navigateToHome.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToHome()
            }
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }

    private fun navigateToHome() {
        Log.d("SplashActivity", "Navigating to HomeActivity")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}