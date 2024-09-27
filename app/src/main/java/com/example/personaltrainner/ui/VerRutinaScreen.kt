import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import java.io.File
import java.io.FileOutputStream
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
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
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VerRutinaScreen(
    clienteId: Int,
    clienteNombre: String,
    clienteApellido: String,
    rutinaViewModel: RutinaViewModel,
    ejercicioViewModel: EjercicioViewModel,
    onEditRutina: (Int) -> Unit // Función para navegar a la pantalla de edición
) {
    val rutinas by rutinaViewModel.obtenerRutinasPorCliente(clienteId).collectAsState(initial = emptyList())
    val context = LocalContext.current
    val ejercicios by ejercicioViewModel.obtenerTodosLosEjercicios().collectAsState(initial = emptyList())

    // Fechas de inicio y fin para filtrar rutinas
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // DatePickers para seleccionar el rango de fechas
    val calendar = Calendar.getInstance()
    val datePickerInicio = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            fechaInicio = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val datePickerFin = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            fechaFin = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

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

        // Selectores de fecha para el rango
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { datePickerInicio.show() }) {
                Text(if (fechaInicio.isEmpty()) "Fecha Inicio" else "Inicio: $fechaInicio")
            }
            Button(onClick = { datePickerFin.show() }) {
                Text(if (fechaFin.isEmpty()) "Fecha Fin" else "Fin: $fechaFin")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para exportar el PDF solo con las rutinas en el rango de fechas
        Button(
            onClick = {
                if (rutinas.isNotEmpty()) {
                    val rutinasFiltradas = rutinas.filter {
                        val fechaRutina = dateFormat.parse(dateFormat.format(it.fecha))
                        val inicio = if (fechaInicio.isNotEmpty()) dateFormat.parse(fechaInicio) else null
                        val fin = if (fechaFin.isNotEmpty()) dateFormat.parse(fechaFin) else null
                        (inicio == null || fechaRutina.after(inicio)) && (fin == null || fechaRutina.before(fin))
                    }
                    if (rutinasFiltradas.isNotEmpty()) {
                        generarPdf(context, rutinasFiltradas, ejercicios, clienteNombre, clienteApellido)
                    } else {
                        Toast.makeText(context, "No hay rutinas en el rango seleccionado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "No hay rutinas para exportar", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Exportar PDF")
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
                    RutinaCard(rutina, ejercicioViewModel, onEditRutina, onDelete = {
                        rutinaViewModel.eliminarRutina(rutina) // Eliminar la rutina
                    })
                }
            }
        }
    }
}

@Composable
fun RutinaCard(
    rutina: RutinaEntity,
    ejercicioViewModel: EjercicioViewModel,
    onEdit: (Int) -> Unit, // Callback para editar
    onDelete: () -> Unit   // Callback para eliminar
) {
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

            Spacer(modifier = Modifier.height(8.dp))

            // Botones Editar y Eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onEdit(rutina.id) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = { onDelete() },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            }
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

fun generarPdf(context: Context, rutinas: List<RutinaEntity>, ejercicios: List<EjercicioEntity>, clienteNombre: String, clienteApellido: String) {
    val pdfDocument = PdfDocument()
    val paint = Paint()

    // Tamaño de la página en A4
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    var page = pdfDocument.startPage(pageInfo)
    var canvas: Canvas = page.canvas

    // Variables para controlar el diseño
    var yPosition = 50f
    paint.textSize = 20f
    paint.isFakeBoldText = true
    canvas.drawText("Rutinas de $clienteNombre $clienteApellido", 20f, yPosition, paint)

    yPosition += 40f

    paint.textSize = 14f
    paint.isFakeBoldText = false
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Recorrer las rutinas y agregar al PDF
    for (rutina in rutinas) {
        if (yPosition > 750f) { // Si la página está llena, agregar una nueva
            pdfDocument.finishPage(page)
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            yPosition = 50f
        }

        // Obtener el nombre del ejercicio basado en el ID
        val ejercicio = ejercicios.find { it.id == rutina.ejercicioId }
        val ejercicioNombre = ejercicio?.nombre ?: "Desconocido"

        // Encabezado de cada rutina con línea divisoria
        paint.textSize = 16f
        paint.isFakeBoldText = true
        canvas.drawText("Rutina del ${dateFormat.format(rutina.fecha)}", 20f, yPosition, paint)
        yPosition += 10f
        canvas.drawLine(20f, yPosition, 575f, yPosition, paint)
        yPosition += 20f

        // Detalles de la rutina
        paint.textSize = 14f
        paint.isFakeBoldText = false
        canvas.drawText("Ejercicio: $ejercicioNombre", 20f, yPosition, paint)
        yPosition += 20f
        canvas.drawText("Repeticiones: ${rutina.repeticiones}", 20f, yPosition, paint)
        yPosition += 20f
        canvas.drawText("Series: ${rutina.series}", 20f, yPosition, paint)
        yPosition += 30f

        // Dibujar una línea separadora después de cada rutina
        paint.style = Paint.Style.STROKE
        canvas.drawLine(20f, yPosition, 575f, yPosition, paint)
        paint.style = Paint.Style.FILL
        yPosition += 20f
    }

    // Finalizar la última página
    pdfDocument.finishPage(page)

    // Guardar el PDF en el almacenamiento externo
    val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
    val fileName = "Rutinas_${clienteNombre}_${clienteApellido}_${System.currentTimeMillis()}.pdf"
    val file = File(directoryPath, fileName)

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF guardado en: $directoryPath/$fileName", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error al generar PDF", Toast.LENGTH_SHORT).show()
    }

    pdfDocument.close()
}