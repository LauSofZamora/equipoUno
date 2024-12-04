package com.example.equipouno.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    init {
        Log.d("SplashViewModel", "ViewModel initialized")
        startSplashTimer()
    }

    private fun startSplashTimer() {
        viewModelScope.launch {
            Log.d("SplashViewModel", "Timer started")
            delay(5000) // 5 segundos
            Log.d("SplashViewModel", "Timer finished, setting navigation to true")
            _navigateToHome.postValue(true)
        }
    }
    }