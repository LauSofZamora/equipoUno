package com.example.equipouno.viewmodel

import androidx.lifecycle.ViewModel
import com.example.equipouno.repository.LoginRepository

class LoginViewModel : ViewModel(){
    private val repository = LoginRepository()

    fun registerUser(email: String, pass: String, isRegister: (Boolean) -> Unit) {

        repository.registerUser(email,pass) { response ->
            isRegister(response)
        }
    }

    fun loginUser(email: String, pass: String, isLoginSuccessful: (Boolean) -> Unit) {
        repository.loginUser(email, pass) { response ->
            isLoginSuccessful(response)
        }
    }

}