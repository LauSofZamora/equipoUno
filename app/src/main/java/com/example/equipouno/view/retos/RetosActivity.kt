package com.example.equipouno.view.retos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipouno.R
import kotlinx.android.synthetic.main.activity_retos.*

class RetosActivity : AppCompatActivity() {

    private val retos = mutableListOf(
        "Realiza un RecyclerView que liste una API de Pokemones",
        "Crea un botón que al dar clic muestre una cuenta regresiva de 5 a 0",
        "Crea un botón que al dar clic cambie de color y lance un mensaje emergente que diga 'Hola Mundo'"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retos)

        recyclerViewRetos.layoutManager = LinearLayoutManager(this)
        recyclerViewRetos.adapter = RetosAdapter(retos, { position ->
            // Lógica para editar un reto
        }, { position ->
            // Lógica para eliminar un reto
            retos.removeAt(position)
            recyclerViewRetos.adapter?.notifyItemRemoved(position)
        })

        fabAddReto.setOnClickListener {
            // Lógica para agregar un nuevo reto
        }
    }
}
