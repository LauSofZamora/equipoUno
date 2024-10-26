import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.equipouno.R
import com.example.equipouno.model.Reto

class RetosAdapter : ListAdapter<Reto, RetosAdapter.RetoViewHolder>(RetoDiffCallback()) {

    class RetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconoReto: ImageView = itemView.findViewById(R.id.iconReto)
        val descripcionReto: TextView = itemView.findViewById(R.id.descriptionReto)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btn_edit_reto)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btn_delete_reto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reto, parent, false)
        return RetoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RetoViewHolder, position: Int) {
        val reto = getItem(position)
        holder.descripcionReto.text = reto.descripcion

        holder.btnEditar.setOnClickListener {
            // Lógica para editar reto
        }

        holder.btnEliminar.setOnClickListener {
            // Lógica para eliminar reto
        }
    }
}

class RetoDiffCallback : DiffUtil.ItemCallback<Reto>() {
    override fun areItemsTheSame(oldItem: Reto, newItem: Reto): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Reto, newItem: Reto): Boolean = oldItem == newItem
}
