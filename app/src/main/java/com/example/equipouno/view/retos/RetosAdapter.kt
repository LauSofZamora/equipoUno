package com.example.equipouno.view.retos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.equipouno.R

// Clase del reto para simular los datos (puedes adaptar esto según tu modelo de datos)
data class Reto(val descripcion: String, val icono: Int)

class RetosAdapter(private val retosList: MutableList<Reto> = mutableListOf()) :
    RecyclerView.Adapter<RetosAdapter.RetoViewHolder>() {

    // ViewHolder para los elementos de la lista
    class RetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconoReto: ImageView = itemView.findViewById(R.id.iconReto)
        val descripcionReto: TextView = itemView.findViewById(R.id.descriptionReto)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btn_edit_reto)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btn_delete_reto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetoViewHolder {
        // Inflar el layout item_reto.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reto, parent, false)
        return RetoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RetoViewHolder, position: Int) {
        val reto = retosList[position]

        // Vincular los datos con las vistas
        holder.iconoReto.setImageResource(reto.icono)
        holder.descripcionReto.text = reto.descripcion

        // Manejo del botón de editar (Criterio 9)
        holder.btnEditar.setOnClickListener {
            // Lanza cuadro de diálogo para editar reto
            // Aquí puedes implementar la lógica para editar el reto
        }

        // Manejo del botón de eliminar (Criterio 10)
        holder.btnEliminar.setOnClickListener {
            // Lanza cuadro de diálogo para eliminar reto
            // Aquí puedes implementar la lógica para eliminar el reto
        }
    }

    override fun getItemCount(): Int = retosList.size

    // Función para agregar un reto y actualizar la lista
    fun agregarReto(nuevoReto: Reto) {
        retosList.add(0, nuevoReto) // Agrega el reto en la parte superior (Criterio 6)
        notifyItemInserted(0)
    }

    // Función para eliminar un reto
    fun eliminarReto(position: Int) {
        retosList.removeAt(position)
        notifyItemRemoved(position)
    }
}
