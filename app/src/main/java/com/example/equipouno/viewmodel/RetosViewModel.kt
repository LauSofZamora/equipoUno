import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.example.equipouno.model.Reto
import kotlinx.coroutines.launch

class RetosViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val retosCollection = firestore.collection("retos") // Referencia a la colección "retos"

    private val _retos = MutableLiveData<List<Reto>>()
    val retos: LiveData<List<Reto>> get() = _retos

    // Cargar los retos desde Firebase Firestore
    fun cargarRetos() {
        firestore.collection("retos").get()
            .addOnSuccessListener { result ->
                val listaRetos = result.documents.map { doc ->
                    // Obtener la descripcion de manera segura y convertirlo a String
                    val descripcion = doc.getString("descripcion") ?: "Sin descripción"
                    Reto(
                        id = doc.id,
                        descripcion = descripcion
                    )
                }
                _retos.value = listaRetos
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                _retos.value = emptyList()
            }
    }



    // Agregar un nuevo reto a Firestore
    fun agregarReto(descripcion: String) {
        viewModelScope.launch {
            val nuevoReto = hashMapOf("descripcion" to descripcion)
            retosCollection.add(nuevoReto) // Agrega un nuevo reto a la colección
                .addOnSuccessListener {
                    cargarRetos() // Recargar la lista de retos
                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Maneja cualquier error
                }
        }
    }

    // Editar un reto existente
    fun editarReto(reto: Reto, nuevaDescripcion: String) {
        viewModelScope.launch {
            retosCollection.document(reto.id)
                .update("descripcion", nuevaDescripcion) // Actualiza el campo "descripcion"
                .addOnSuccessListener {
                    cargarRetos() // Recargar la lista de retos
                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Maneja cualquier error
                }
        }
    }

    // Eliminar un reto
    fun eliminarReto(reto: Reto) {
        viewModelScope.launch {
            retosCollection.document(reto.id).delete() // Elimina el reto
                .addOnSuccessListener {
                    cargarRetos() // Recargar la lista de retos
                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Maneja cualquier error
                }
        }
    }
}
