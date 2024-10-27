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
import com.example.equipouno.viewmodel.RetosViewModel

class RetosAdapter(
    private val viewModel: RetosViewModel,  // Para las operaciones de base de datos
    private val onEditClick: (Reto) -> Unit  // Para manejar el click de edición
) : ListAdapter<Reto, RetosAdapter.RetoViewHolder>(RetoDiffCallback()) {

    class RetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconoReto: ImageView = itemView.findViewById(R.id.iconReto)
        val descripcionReto: TextView = itemView.findViewById(R.id.descriptionReto)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btn_edit_reto)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btn_delete_reto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reto, parent, false)
        return RetoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RetoViewHolder, position: Int) {
        val reto = getItem(position)

        // Configurar el texto de la descripción
        holder.descripcionReto.text = reto.descripcion

        // Click en el botón editar
        holder.btnEditar.setOnClickListener {
            onEditClick(reto)  // Llama al callback de edición
        }

        // Click en el botón eliminar
        holder.btnEliminar.setOnClickListener {
            viewModel.eliminarReto(reto)
        }
    }
}

class RetoDiffCallback : DiffUtil.ItemCallback<Reto>() {
    override fun areItemsTheSame(oldItem: Reto, newItem: Reto): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Reto, newItem: Reto): Boolean = oldItem == newItem
}
