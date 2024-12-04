package com.example.equipouno.view.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.equipouno.R
import com.example.equipouno.ServicesWeb.ApiUtils
import com.example.equipouno.data.pokemonDTO.pokemonResponse
import com.example.equipouno.view.instrucciones.InstruccionesActivity
import com.example.equipouno.view.login.LoginActivity
import com.example.equipouno.view.retos.RetosActivity
import kotlinx.coroutines.launch
import com.squareup.picasso.Picasso
import kotlin.random.Random
import com.example.equipouno.viewmodel.HomeViewModel
import com.example.equipouno.viewmodel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import com.example.equipouno.Utilitis.SessionManager

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val pokeViewModel: PokeViewModel by viewModels()
    private lateinit var viewModel: HomeViewModel
    private lateinit var timerText: TextView
    private lateinit var bottleImage: ImageView
    private lateinit var blinkingButton: Button
    private var isSpinning = false
    private var spinDirection = 0f
    private lateinit var blinkHandler: Handler
    private lateinit var blinkRunnable: Runnable
    private var mediaPlayer: MediaPlayer? = null
    private var isMuted = false
    private var bottleSpinPlayer: MediaPlayer? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val sessionManager = SessionManager(this)

        val logoutButton = findViewById<ImageView>(R.id.ic_logout)
        logoutButton.setOnClickListener {
            // Limpiar la sesión
            sessionManager.clearSession()

            // Redirigir al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // Animación al tocar el botón
            animateTouch(logoutButton)

            // Mensaje de confirmación
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
        }

        val start: ImageView = findViewById(R.id.ic_star)
        start.setOnClickListener {
            openPlayStore()
            animateTouch(start)
        }

        val volumeIcon: ImageView = findViewById(R.id.ic_volume_high)
        volumeIcon.setOnClickListener {
            toggleMusic()
            animateTouch(volumeIcon)
        }

        val controllerIcon: ImageView = findViewById(R.id.ic_controller)
        controllerIcon.setOnClickListener {
            val intent = Intent(this, InstruccionesActivity::class.java)
            startActivity(intent)
            animateTouch(controllerIcon)
        }

        val addIcon: ImageView = findViewById(R.id.ic_add)
        addIcon.setOnClickListener {
            val intent = Intent(this, RetosActivity::class.java)
            startActivity(intent)
            animateTouch(addIcon)
        }

        val shareIcon: ImageView = findViewById(R.id.ic_share)
        shareIcon.setOnClickListener {
            shareContent()
            animateTouch(shareIcon)
        }

        // Configura la Toolbar
        val toolbar: Toolbar = findViewById(R.id.customToolbar)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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

        // Iniciar el sonido de fondo
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer?.isLooping = true // Para que se repita en bucle
        mediaPlayer?.start() // Iniciar reproducción
    }

    private fun openPlayStore() {
        val playStoreUrl =
            "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es&pli=1"
        try {
            // Intenta abrir el enlace de la Play Store directamente en el navegador
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl)))
        } catch (e: ActivityNotFoundException) {
            // Si no se encuentra la aplicación de navegador, manejar el error
            Toast.makeText(this, "No se puede abrir la Play Store", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleMusic() {
        val volumeIcon: ImageView = findViewById(R.id.ic_volume_high)
        val silencedOverlay: ImageView = findViewById(R.id.silencedOverlay)

        if (isMuted) {
            // Activar el audio
            mediaPlayer?.start()
            // Cambiar icono a volumen alto
            volumeIcon.setImageResource(R.drawable.ic_volume_high)
        } else {
            // Silenciar el audio
            mediaPlayer?.pause()
            // Cambiar icono a volumen silenciado
            volumeIcon.setImageResource(R.drawable.ic_volume_off)
        }
        volumeIcon.invalidate()
        isMuted = !isMuted // Cambiar el estado de silencio
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun shareContent() {
        // Crea el Intent de compartir
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "App pico botella\nSolo los valientes lo juegan !!\nhttps://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es "
            )
        }
        // Verifica si hay aplicaciones disponibles para manejar el Intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Compartir con"))
        }
    }

    private fun animateTouch(view: View) {
        val scaleDown = ScaleAnimation(
            0.9f,
            1.0f,
            0.9f,
            1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X (centro)
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot Y (centro)
        ).apply {
            duration = 200
            fillAfter = true
        }
        view.startAnimation(scaleDown)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Detener y liberar el MediaPlayer cuando la actividad se destruya
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        // Guardar si la música estaba sonando antes de pausar la actividad
        wasPlayingBeforePause = mediaPlayer?.isPlaying == true
        // Pausar la música si estaba sonando
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        // Reanudar la música solo si no está silenciada y estaba sonando antes de la pausa
        if (!isMuted && wasPlayingBeforePause) {
            mediaPlayer?.start()
        }
    }

    private var wasPlayingBeforePause = false // Estado previo del audio

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
                    fadeIn.start()
                }
            }
        })
        fadeIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (blinkingButton.visibility == View.VISIBLE) {
                    fadeOut.start()
                }
            }
        })
        fadeOut.start()
    }

    private fun startSpinning() {
        isSpinning = true
        blinkingButton.clearAnimation()
        blinkingButton.visibility = View.GONE
        val spinDuration = Random.nextInt(3000, 5000).toLong()
        val spinAmount = Random.nextFloat() * 2080 + 1200
        // Pausar música de fondo si está sonando
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
        playBottleSpinSound()
        ObjectAnimator.ofFloat(bottleImage, "rotation", spinDirection, spinDirection + spinAmount)
            .apply {
                duration = spinDuration
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        spinDirection =
                            (spinDirection + spinAmount) % 360
                        stopBottleSpinSound()
                        showCountdown() // Mostrar la cuenta regresiva
                    }
                })
                start()
            }
    }

    private fun playBottleSpinSound() {
        if (bottleSpinPlayer == null) {
            bottleSpinPlayer = MediaPlayer.create(this, R.raw.bottle_spining)
        }
        bottleSpinPlayer?.start()
    }

    private fun stopBottleSpinSound() {
        bottleSpinPlayer?.stop()
        bottleSpinPlayer?.release() // Liberar el MediaPlayer
        bottleSpinPlayer = null
    }

    private fun showCountdown() {
        val handler = Handler(Looper.getMainLooper())
        var count = 3
        timerText.text = count.toString()
        timerText.visibility = View.VISIBLE
        val runnable = object : Runnable {
            override fun run() {
                if (count > 0) {
                    count--
                    timerText.text = count.toString()
                    handler.postDelayed(this, 1000)
                } else {
                    timerText.visibility = View.GONE
                    blinkingButton.visibility = View.VISIBLE
                    startBlinkingButton()
                    isSpinning = false

                    showCustomDialog()
                }
            }
        }
        handler.post(runnable)
    }

    private fun showCustomDialog() {
        if (isDestroyed || isFinishing) return

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Deshabilitar cierre al tocar fuera del diálogo
        dialog.setCanceledOnTouchOutside(false)

        // Pausar la música si está sonando
        wasPlayingBeforeDialog = mediaPlayer?.isPlaying == true
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }

        // Obtener y mostrar el reto aleatorio
        val txtReto = dialog.findViewById<TextView>(R.id.dialogTitle)
        viewModel?.getRandomReto { reto ->
            txtReto.text = reto
        }

        val pokemonImageView = dialog.findViewById<ImageView>(R.id.circleImageView)

        lifecycleScope.launch {
            try {
                val response: Response<pokemonResponse> = ApiUtils.getApiService().getPokemons()

                if (response.isSuccessful) {
                    val pokemonList = response.body()?.pokemonList

                    if (pokemonList != null && pokemonList.isNotEmpty()) {
                        val randomIndex = Random.nextInt(pokemonList.size)
                        val randomPokemon = pokemonList[randomIndex]
                        val imagenUrl = randomPokemon.img
                        val imagenUrlHttps = imagenUrl.replaceFirst("http", "https")

                        val pokemonImageView = dialog.findViewById<ImageView>(R.id.circleImageView)

                        Picasso.get()
                            .load(imagenUrlHttps)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .resize(135, 135)
                            .centerCrop()
                            .into(pokemonImageView)

                        println("Pokemon: Image: ${randomPokemon.img}")
                    }
                } else {
                    println("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
            }
        }

        val btnDismiss = dialog.findViewById<Button>(R.id.btnDismiss)
        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            // Reanudar música si no está silenciada
            if (!isMuted) {
                mediaPlayer?.start()
            }
        }
        dialog.show()
    }

    private var wasPlayingBeforeDialog = false // Variable para rastrear el estado antes del diálogo
}