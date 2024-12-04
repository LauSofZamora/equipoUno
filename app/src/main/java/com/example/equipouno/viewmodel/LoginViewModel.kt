package com.example.equipouno.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    fun loginUser(email: String, pass: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun registerUser(email: String, pass: String, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }
}


//@HiltViewModel
//class LoginViewModel @Inject constructor(
//    private val auth: FirebaseAuth = Firebase.auth // Inyección de FirebaseAuth
//) : ViewModel() {
//
//    fun registerUser(email: String, password: String, callback: (Boolean) -> Unit) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Usuario registrado exitosamente
//                    Log.d("Firebase", "Usuario registrado: ${task.result?.user?.uid}")
//                    callback(true)
//                } else {
//                    // Error en el registro
//                    Log.e("Firebase", "Error en registro", task.exception)
//                    callback(false)
//                }
//            }
//    }
//
//    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Login exitoso
//                    Log.d("Firebase", "Usuario logueado: ${task.result?.user?.uid}")
//                    callback(true)
//                } else {
//                    // Error en el login
//                    Log.e("Firebase", "Error en login", task.exception)
//                    callback(false)
//                }
//            }
//    }
//}