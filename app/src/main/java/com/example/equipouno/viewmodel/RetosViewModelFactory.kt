//package com.example.equipouno.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.equipouno.database.DatabaseHelper
//
//class RetosViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RetosViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return RetosViewModel(dbHelper) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
