package com.example.equipouno.view.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.equipouno.databinding.ActivityLoginBinding
import com.example.equipouno.view.home.HomeActivity
import com.example.equipouno.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura listeners
        setupListeners()
    }

    private fun setupListeners() {
        // Validación de contraseña
        binding.passwordInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.length < 6) {
                    binding.passwordInputLayout.error = "Mínimo 6 dígitos"
                    binding.passwordInputLayout.boxStrokeColor = Color.RED
                } else {
                    binding.passwordInputLayout.error = null
                    binding.passwordInputLayout.boxStrokeColor = Color.WHITE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validación para habilitar botones
        val enableButtons = {
            val isEmailNotEmpty = binding.emailInput.text.toString().isNotEmpty()
            val isPasswordValid = binding.passwordInput.text.toString().length >= 6
            val isFormValid = isEmailNotEmpty && isPasswordValid

            // Habilita o deshabilita "Registrarse"
            binding.tvRegister.isEnabled = isFormValid
            binding.tvRegister.setTextColor(
                if (isFormValid) Color.WHITE else Color.parseColor("#9EA1A1")
            )

            // Habilita o deshabilita "Login"
            binding.loginButton.isEnabled = isFormValid
            binding.loginButton.alpha = if (isFormValid) 1.0f else 0.5f
        }

        binding.emailInput.addTextChangedListener { enableButtons() }
        binding.passwordInput.addTextChangedListener { enableButtons() }

        // Acción para el botón de "Login"****
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val pass = binding.passwordInput.text.toString()
            loginUser(email, pass)
        }

        // Acción para el botón de "Registrarse"****
        binding.tvRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.emailInput.text.toString()
        val pass = binding.passwordInput.text.toString()

        loginViewModel.registerUser(email, pass) { isRegister ->
            if (isRegister) {
                goToHome(email)
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, pass: String) {
        // Aquí puedes agregar la lógica de login
        loginViewModel.loginUser(email, pass) { isLoginSuccessful ->
            if (isLoginSuccessful) {
                goToHome(email)
            } else {
                Toast.makeText(this, "Login fallido, revisa tus credenciales", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome(email: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("EMAIL", email)
        startActivity(intent)
        finish() // Finaliza la actividad actual
    }
}