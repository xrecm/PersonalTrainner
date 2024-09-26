package com.example.personaltrainner.ui

import android.app.DatePickerDialog
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.foundation.layout.*
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
fun RutinaEditarScreen(
    rutinaId: Int,
    viewModel: RutinaViewModel,
    clientes: List<ClienteEntity>,
    ejercicios: List<EjercicioEntity>,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    // Obtener la rutina por ID y guardar en estado
    val rutina by viewModel.obtenerRutinaPorId(rutinaId).collectAsState(initial = null)

    // Inicializamos los campos con los valores de la rutina para editar
    var selectedCliente by remember(rutina) { mutableStateOf(rutina?.clienteId ?: 0) }
    var selectedEjercicio by remember(rutina) { mutableStateOf(rutina?.ejercicioId ?: 0) }
    var repeticiones by remember(rutina) { mutableStateOf(rutina?.repeticiones?.toString() ?: "") }
    var series by remember(rutina) { mutableStateOf(rutina?.series?.toString() ?: "") }
    var fechaSeleccionada by remember(rutina) { mutableStateOf(SimpleDateFormat("dd/MM/yyyy").format(rutina?.fecha ?: Date())) }

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
            text = "Editar Rutina",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Dropdown para seleccionar un cliente
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { /* Lógica para expandir el menú */ }
        ) {
            OutlinedTextField(
                value = clientes.find { it.id == selectedCliente }?.nombre ?: "Seleccionar Cliente",
                onValueChange = {},
                label = { Text("Seleccionar Cliente", color = Color.Black) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Menú desplegable para seleccionar el cliente
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = { /* Lógica para cerrar el menú */ }
            ) {
                clientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text(cliente.nombre) },
                        onClick = {
                            selectedCliente = cliente.id
                        }
                    )
                }
            }
        }

        // Dropdown para seleccionar un ejercicio
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { /* Lógica para expandir el menú */ }
        ) {
            OutlinedTextField(
                value = ejercicios.find { it.id == selectedEjercicio }?.nombre ?: "Seleccionar Ejercicio",
                onValueChange = {},
                label = { Text("Seleccionar Ejercicio", color = Color.Black) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Menú desplegable para seleccionar el ejercicio
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = { /* Lógica para cerrar el menú */ }
            ) {
                ejercicios.forEach { ejercicio ->
                    DropdownMenuItem(
                        text = { Text(ejercicio.nombre) },
                        onClick = {
                            selectedEjercicio = ejercicio.id
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
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la cantidad de series
        OutlinedTextField(
            value = series,
            onValueChange = { series = it },
            label = { Text("Cantidad de Series", color = Color.Black) },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para mostrar el calendario y seleccionar la fecha
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (fechaSeleccionada.isEmpty()) "Seleccionar Fecha" else "Fecha: ${fechaSeleccionada}")
        }

        // Botón para guardar
        Button(
            onClick = {
                if (selectedCliente != 0 && selectedEjercicio != 0 && fechaSeleccionada.isNotEmpty() && repeticiones.isNotEmpty() && series.isNotEmpty()) {
                    viewModel.actualizarRutina(
                        RutinaEntity(
                            id = rutinaId,
                            clienteId = selectedCliente,
                            ejercicioId = selectedEjercicio,
                            fecha = SimpleDateFormat("dd/MM/yyyy").parse(fechaSeleccionada),
                            repeticiones = repeticiones.toInt(),
                            series = series.toInt()
                        )
                    )
                    onSave()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Rutina")
        }

        // Botón para cancelar
        Button(
            onClick = { onCancel() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
