package com.example.personaltrainner.ui

import android.app.DatePickerDialog
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    ejercicios: List<EjercicioEntity>
) {
    // Variables de estado para el formulario
    var expandedCliente by remember { mutableStateOf(false) }
    var expandedEjercicio by remember { mutableStateOf(false) }
    var selectedCliente by remember { mutableStateOf(clientes.firstOrNull()?.nombre ?: "") }
    var selectedEjercicio by remember { mutableStateOf(ejercicios.firstOrNull()?.nombre ?: "") }
    var selectedClienteId by remember { mutableStateOf(clientes.firstOrNull()?.id ?: 0) }
    var selectedEjercicioId by remember { mutableStateOf(ejercicios.firstOrNull()?.id ?: 0) }
    var repeticiones by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf("") }

    // Contexto necesario para mostrar el DatePicker
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
        // Título
        Text(
            text = "Crear Rutina",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Dropdown para seleccionar un cliente
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Black
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCliente)
                },
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

        // Dropdown para seleccionar un ejercicio
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Black
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEjercicio)
                },
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

        // Campo de texto para la cantidad de repeticiones
        OutlinedTextField(
            value = repeticiones,
            onValueChange = { repeticiones = it },
            label = { Text("Cantidad de Repeticiones", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la cantidad de series
        OutlinedTextField(
            value = series,
            onValueChange = { series = it },
            label = { Text("Cantidad de Series", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para mostrar el calendario y seleccionar la fecha
        Button(
            onClick = { datePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (fechaSeleccionada.isEmpty()) "Seleccionar Fecha" else "Fecha: $fechaSeleccionada")
        }

        // Botón para guardar el plan semanal
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
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Rutina")
        }

        // Espacio para mostrar la lista de planes semanales guardados
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de planes semanales guardados
        val rutinas by viewModel.obtenerTodosLosPlanes(1).collectAsState(initial = emptyList()) // Cambia el clienteId según sea necesario
        LazyColumn {
            items(rutinas) { rut ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Cliente: ${rut.clienteId}")
                        Text(text = "Ejercicio: ${rut.ejercicioId}")
                        Text(text = "Fecha: ${SimpleDateFormat("dd/MM/yyyy").format(rut.fecha)}")
                        Text(text = "Repeticiones: ${rut.repeticiones}")
                        Text(text = "Series: ${rut.series}")
                    }
                }
            }
        }
    }
}
