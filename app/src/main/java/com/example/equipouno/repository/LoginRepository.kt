package com.example.equipouno.repository;

import com.google.firebase.auth.FirebaseAuth

class LoginRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, pass: String, isRegisterComplete: (Boolean) -> Unit) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        isRegisterComplete(true)
                    } else {
                        isRegisterComplete(false)
                    }
                }
                .addOnFailureListener { exception ->
                    // Mostrar error si ocurre
                    exception.printStackTrace()
                    isRegisterComplete(false)
                }
        } else {
            isRegisterComplete(false)
        }
    }

    fun loginUser(email: String, pass: String, isLoginComplete: (Boolean) -> Unit) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLoginComplete(true)
                    } else {
                        isLoginComplete(false)
                    }
                }
        } else {
            isLoginComplete(false)
        }
    }

}