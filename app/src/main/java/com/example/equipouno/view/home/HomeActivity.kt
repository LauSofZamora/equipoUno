package com.example.equipouno.view.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.equipouno.R
import com.example.equipouno.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "onCreate called")
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Aquí puedes observar los LiveData del ViewModel y actualizar la UI
    }
}