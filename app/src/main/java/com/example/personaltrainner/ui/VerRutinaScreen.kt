package com.example.personaltrainner.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.business.RutinaViewModel
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.business.prototype.ConcretePrototype
import com.example.personaltrainner.business.prototype.RutinaCloner
import com.example.personaltrainner.business.strategy.ExportContext
import com.example.personaltrainner.business.strategy.ExportarPDF
import com.example.personaltrainner.business.strategy.ExportarCSV
import com.example.personaltrainner.business.strategy.ExportarExcel
import com.example.personaltrainner.data.ClienteEntity
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
    clientesDisponibles: List<ClienteEntity>,
    onEditRutina: (Int) -> Unit,// Función para navegar a la pantalla de edición
) {
    val rutinas by rutinaViewModel.obtenerRutinasPorCliente(clienteId).collectAsState(initial = emptyList())
    val context = LocalContext.current
    val ejercicios by ejercicioViewModel.obtenerTodosLosEjercicios().collectAsState(initial = emptyList())
    val cloner = RutinaCloner()


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

    // Estados para el diálogo
    var showCloneDialog by remember { mutableStateOf(false) }
    var rutinaSeleccionada by remember { mutableStateOf<RutinaEntity?>(null) }
    var clienteSeleccionado by remember { mutableStateOf<ClienteEntity?>(null) }
    var nuevaFecha by remember { mutableStateOf("") }

    // Mostrar diálogo de clonación
    if (showCloneDialog) {
        CloneDialog(
            rutina = rutinaSeleccionada,
            clientes = clientesDisponibles,
            clienteSeleccionado = clienteSeleccionado,
            nuevaFecha = nuevaFecha,
            onClienteSeleccionado = { clienteSeleccionado = it },
            onFechaChange = { nuevaFecha = it },
            onCancel = { showCloneDialog = false },
            onConfirm = {
                showCloneDialog = false
                rutinaSeleccionada?.let { rutina ->
                    clienteSeleccionado?.let { cliente ->
                        try {
                            val parsedFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(nuevaFecha)
                            val rutinaClonada = cloner.clonarRutina(
                                ConcretePrototype(
                                    id = rutina.id,
                                    clienteId = rutina.clienteId,
                                    ejercicioId = rutina.ejercicioId,
                                    fecha = rutina.fecha,
                                    repeticiones = rutina.repeticiones,
                                    series = rutina.series
                                ), cliente.id, parsedFecha!!
                            )
                            val rutinaEntity = (rutinaClonada as ConcretePrototype).toRutinaEntity()
                            rutinaViewModel.insertarRutina(rutinaEntity)
                            Toast.makeText(context, "Rutina clonada con éxito", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error al clonar rutina: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )
    }

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
        val exportContext = remember { ExportContext(ExportarPDF()) } // Estrategia inicial
        var formatoSeleccionado by remember { mutableStateOf("PDF") }

        // Dropdown para seleccionar formato de exportación
        DropdownMenuDemo(
            selectedFormato = formatoSeleccionado,
            onFormatoSeleccionado = { formato ->
                formatoSeleccionado = formato
                exportContext.setStrategy(
                    when (formato) {
                        "Excel" -> ExportarExcel()
                        "CSV" -> ExportarCSV()
                        "PDF" -> ExportarPDF()
                        else -> ExportarPDF() // Default
                    }
                )
            }
        )
        // Botón para exportar
        Button(onClick = {
            if (rutinas.isNotEmpty()) {
                val datos = rutinas.map { rutina ->
                    val ejercicio = ejercicios.find { it.id == rutina.ejercicioId }?.nombre ?: "Desconocido"
                    "Cliente: $clienteNombre $clienteApellido, Ejercicio: $ejercicio, Repeticiones: ${rutina.repeticiones}, Series: ${rutina.series}, Fecha: ${rutina.fecha}"
                }
                val resultado = exportContext.exportar(context, rutinas, ejercicios)
                Toast.makeText(context, resultado, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "No hay rutinas para exportar", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Exportar en $formatoSeleccionado")
        }

        // Mostrar lista de rutinas
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
        } else{
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(rutinas) { rutina ->
                    RutinaCard(
                        rutina = rutina,
                        ejercicioViewModel = ejercicioViewModel,
                        onEdit = onEditRutina,
                        onDelete = { rutinaViewModel.eliminarRutina(rutina) },
                        onClone = {
                            rutinaSeleccionada = rutina
                            showCloneDialog = true
                        }
                    )
                }
            }
        }


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

@Composable
fun DropdownMenuDemo(
    selectedFormato: String,
    onFormatoSeleccionado: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val formatos = listOf("Excel", "CSV", "PDF")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Formato seleccionado: $selectedFormato", modifier = Modifier.padding(8.dp))
        Button(onClick = { expanded = true }) {
            Text("Seleccionar Formato")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            formatos.forEach { formato ->
                DropdownMenuItem(
                    text = { Text(formato) },
                    onClick = {
                        onFormatoSeleccionado(formato)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun RutinaCard(
    rutina: RutinaEntity,
    ejercicioViewModel: EjercicioViewModel,
    onEdit: (Int) -> Unit, // Callback para editar
    onDelete: () -> Unit,   // Callback para eliminar
    onClone: (Int) -> Unit  // Callback para clonar
) {
    val ejercicio by ejercicioViewModel.obtenerEjercicioPorId(rutina.ejercicioId).collectAsState(initial = null)
    val fechaFormateada = formatearFecha(rutina.fecha)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
            )
            Text(
                text = "Repeticiones: ${rutina.repeticiones}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Series: ${rutina.series}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onEdit(rutina.id) }) {
                    Text("Editar")
                }
                Button(onClick = { onDelete() }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                    Text("Eliminar")
                }
                Button(onClick = {
                    val nuevoClienteId = 101 // Aquí puedes solicitar el nuevo clienteId dinámicamente
                    onClone(nuevoClienteId)
                }) {
                    Text("Clonar")
                }
            }
        }
    }
}

@Composable
fun CloneDialog(
    rutina: RutinaEntity?,
    clientes: List<ClienteEntity>,
    clienteSeleccionado: ClienteEntity?,
    nuevaFecha: String,
    onClienteSeleccionado: (ClienteEntity) -> Unit,
    onFechaChange: (String) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("Clonar Rutina") },
        text = {
            Column {
                Text("Selecciona el Cliente:")
                LazyColumn(modifier = Modifier.height(150.dp)) {
                    items(clientes) { cliente ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onClienteSeleccionado(cliente) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = clienteSeleccionado?.id == cliente.id,
                                onClick = { onClienteSeleccionado(cliente) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${cliente.nombre} ${cliente.apellido}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Selecciona la Fecha:")
                TextField(
                    value = nuevaFecha,
                    onValueChange = onFechaChange,
                    label = { Text("Nueva Fecha (dd/MM/yyyy)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = clienteSeleccionado != null && nuevaFecha.isNotEmpty()
            ) {
                Text("Clonar")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}
