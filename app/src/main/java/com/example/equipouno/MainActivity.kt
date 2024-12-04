package com.example.equipouno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import com.example.equipouno.ui.theme.EquipoUnoTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            EquipoUnoTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xCCFFFFFF)) // Fondo semi-transparente blanco
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Hello $name!")

            BlinkingButton()
        }
    }
}

@Composable
fun BlinkingButton() {
    var isVisible by remember { mutableStateOf(true) }

    // Efecto para alternar la visibilidad del botón
    LaunchedEffect(Unit) {
        while (true) {
            isVisible = !isVisible // Alterna la visibilidad
            delay(500) // Espera 500 ms
        }
    }

    // Muestra el botón si isVisible es verdadero
    if (isVisible) {
        Button(
            onClick = { /* Acción al presionar el botón */ },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), // Para ocupar todo el ancho
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500) // Color naranja brillante
            )
        ) {
            Text(text = "Presióname", color = Color.White) // Texto en blanco
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    EquipoUnoTheme {
//        Greeting("Android")
//    }
//}
