import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.business.RutinaViewModel
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.data.RutinaEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VerRutinaScreen(clienteId: Int, clienteNombre: String, clienteApellido: String, rutinaViewModel: RutinaViewModel, ejercicioViewModel: EjercicioViewModel) {
    val rutinas by rutinaViewModel.obtenerRutinasPorCliente(clienteId).collectAsState(initial = emptyList())
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título con el nombre del cliente
        Text(
            text = "Rutinas de $clienteNombre $clienteApellido",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón para volver
        Button(
            onClick = { (context as? Activity)?.finish() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de rutinas
        if (rutinas.isEmpty()) {
            Text(
                text = "No hay rutinas para mostrar",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(rutinas) { rutina ->
                    RutinaCard(rutina, ejercicioViewModel)
                }
            }
        }
    }
}

@Composable
fun RutinaCard(rutina: RutinaEntity, ejercicioViewModel: EjercicioViewModel) {
    // Recoger el ejercicio correspondiente usando collectAsState
    val ejercicio by ejercicioViewModel.obtenerEjercicioPorId(rutina.ejercicioId).collectAsState(initial = null)
    val fechaFormateada = formatearFecha(rutina.fecha)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Fecha: $fechaFormateada",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "Ejercicio: ${ejercicio?.nombre ?: "Cargando..."}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = "Repeticiones: ${rutina.repeticiones}",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = "Series: ${rutina.series}",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

// Función para formatear la fecha a un formato legible
fun formatearFecha(fecha: Date): String {
    return try {
        val targetFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        targetFormat.format(fecha)
    } catch (e: Exception) {
        fecha.toString()
    }
}
