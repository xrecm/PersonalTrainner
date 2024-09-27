package com.example.personaltrainner.ui

import android.app.DatePickerDialog
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.personaltrainner.business.RutinaViewModel
import com.example.personaltrainner.data.ClienteEntity
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaScreen(
    viewModel: RutinaViewModel,
    clientes: List<ClienteEntity>,
    ejercicios: List<EjercicioEntity>,
    onCancel: () -> Unit // Callback para cancelar y volver atrás
) {
    var expandedCliente by remember { mutableStateOf(false) }
    var expandedEjercicio by remember { mutableStateOf(false) }
    var selectedCliente by remember { mutableStateOf(clientes.firstOrNull()?.nombre ?: "") }
    var selectedEjercicio by remember { mutableStateOf(ejercicios.firstOrNull()?.nombre ?: "") }
    var selectedClienteId by remember { mutableStateOf(clientes.firstOrNull()?.id ?: 0) }
    var selectedEjercicioId by remember { mutableStateOf(ejercicios.firstOrNull()?.id ?: 0) }
    var repeticiones by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // DatePicker para seleccionar la fecha
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            fechaSeleccionada = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Crear Rutina",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Dropdown para seleccionar cliente
        ExposedDropdownMenuBox(
            expanded = expandedCliente,
            onExpandedChange = { expandedCliente = !expandedCliente }
        ) {
            OutlinedTextField(
                value = selectedCliente,
                onValueChange = {},
                label = { Text("Seleccionar Cliente", color = Color.Black) },
                readOnly = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCliente) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandedCliente,
                onDismissRequest = { expandedCliente = false }
            ) {
                clientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text(cliente.nombre) },
                        onClick = {
                            selectedCliente = cliente.nombre
                            selectedClienteId = cliente.id
                            expandedCliente = false
                        }
                    )
                }
            }
        }

        // Dropdown para seleccionar ejercicio
        ExposedDropdownMenuBox(
            expanded = expandedEjercicio,
            onExpandedChange = { expandedEjercicio = !expandedEjercicio }
        ) {
            OutlinedTextField(
                value = selectedEjercicio,
                onValueChange = {},
                label = { Text("Seleccionar Ejercicio", color = Color.Black) },
                readOnly = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEjercicio) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandedEjercicio,
                onDismissRequest = { expandedEjercicio = false }
            ) {
                ejercicios.forEach { ejercicio ->
                    DropdownMenuItem(
                        text = { Text(ejercicio.nombre) },
                        onClick = {
                            selectedEjercicio = ejercicio.nombre
                            selectedEjercicioId = ejercicio.id
                            expandedEjercicio = false
                        }
                    )
                }
            }
        }

        // Campos para repeticiones y series
        OutlinedTextField(
            value = repeticiones,
            onValueChange = { repeticiones = it },
            label = { Text("Repeticiones") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = series,
            onValueChange = { series = it },
            label = { Text("Series") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para seleccionar fecha
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = if (fechaSeleccionada.isEmpty()) "Seleccionar Fecha" else "Fecha: $fechaSeleccionada")
        }

        // Botón para guardar rutina
        Button(
            onClick = {
                if (selectedClienteId != 0 && selectedEjercicioId != 0 && fechaSeleccionada.isNotEmpty() && repeticiones.isNotEmpty() && series.isNotEmpty()) {
                    viewModel.insertarRutina(
                        RutinaEntity(
                            clienteId = selectedClienteId,
                            ejercicioId = selectedEjercicioId,
                            fecha = SimpleDateFormat("dd/MM/yyyy").parse(fechaSeleccionada),
                            repeticiones = repeticiones.toInt(),
                            series = series.toInt()
                        )
                    )

                    // Limpiar los campos después de guardar
                    repeticiones = ""
                    series = ""
                    fechaSeleccionada = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text("Guardar Rutina")
        }

        // Botón para cancelar y volver
        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text("Cancelar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de rutinas recientes (últimas creadas)
        Text(
            text = "Rutinas Recientes",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
        )

        val rutinasRecientes by viewModel.obtenerRutinasRecientes().collectAsState(initial = emptyList())
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(rutinasRecientes) { rutina ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Cliente: ${rutina.clienteId}")
                        Text(text = "Ejercicio: ${rutina.ejercicioId}")
                        Text(text = "Fecha: ${SimpleDateFormat("dd/MM/yyyy").format(rutina.fecha)}")
                        Text(text = "Repeticiones: ${rutina.repeticiones}")
                        Text(text = "Series: ${rutina.series}")
                    }
                }
            }
        }
    }
}
