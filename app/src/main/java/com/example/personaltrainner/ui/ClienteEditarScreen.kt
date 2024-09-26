package com.example.personaltrainner.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.data.ClienteEntity
import com.example.personaltrainner.data.MembresiaEntity
import java.text.SimpleDateFormat
import com.example.personaltrainner.ui.calcularFechaFin
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteEditarScreen(
    viewModel: ClienteViewModel,
    cliente: ClienteEntity,
    membresias: List<MembresiaEntity>,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var nombre by remember { mutableStateOf(cliente.nombre) }
    var apellido by remember { mutableStateOf(cliente.apellido) }
    var telefono by remember { mutableStateOf(cliente.telefono.toString()) }
    var edad by remember { mutableStateOf(cliente.edad.toString()) }
    var sexo by remember { mutableStateOf(cliente.sexo) }
    var tamaño by remember { mutableStateOf(cliente.tamaño.toString()) }
    var peso by remember { mutableStateOf(cliente.peso.toString()) }
    var selectedMembresia by remember { mutableStateOf(cliente.membresiaId?.let { id -> membresias.find { it.id == id } }) }
    var fechaInicio by remember { mutableStateOf(cliente.fechaInicioMembresia ?: "") }
    var fechaFin by remember { mutableStateOf(cliente.fechaFinMembresia ?: "") }
    var expandedSexo by remember { mutableStateOf(false) }
    var expandedMembresia by remember { mutableStateOf(false) }

    val opcionesSexo = listOf("Masculino", "Femenino")

    val context = LocalContext.current
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaInicio = "$dayOfMonth/${month + 1}/$year"
            calcularFechaFin(selectedMembresia, fechaInicio)?.let { fechaFin = it }
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Editar Cliente",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Campos de texto para editar la información del cliente
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
        )

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
        )

        // Dropdown para seleccionar el sexo
        Box {
            OutlinedTextField(
                value = sexo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sexo") },
                trailingIcon = { IconButton(onClick = { expandedSexo = !expandedSexo }) { Icon(Icons.Default.ArrowDropDown, contentDescription = null) } },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
            )
            DropdownMenu(
                expanded = expandedSexo,
                onDismissRequest = { expandedSexo = false }
            ) {
                opcionesSexo.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            sexo = opcion
                            expandedSexo = false
                        }
                    )
                }
            }
        }

        // Dropdown para seleccionar la membresía
        Box {
            OutlinedTextField(
                value = selectedMembresia?.nombre ?: "Seleccionar Membresía",
                onValueChange = {},
                readOnly = true,
                label = { Text("Membresía") },
                trailingIcon = { IconButton(onClick = { expandedMembresia = !expandedMembresia }) { Icon(Icons.Default.ArrowDropDown, contentDescription = null) } },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
            )
            DropdownMenu(
                expanded = expandedMembresia,
                onDismissRequest = { expandedMembresia = false }
            ) {
                membresias.forEach { membresia ->
                    DropdownMenuItem(
                        text = { Text(membresia.nombre) },
                        onClick = {
                            selectedMembresia = membresia
                            expandedMembresia = false
                            // Calcular fecha de fin de la membresía según la selección
                            calcularFechaFin(membresia, fechaInicio)?.let { fechaFin = it }
                        }
                    )
                }
            }
        }

        // Botón para seleccionar la fecha de inicio
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (fechaInicio.isEmpty()) "Seleccionar Fecha de Inicio" else "Fecha de Inicio: $fechaInicio")
        }

        // Mostrar la fecha de fin calculada
        if (fechaFin.isNotEmpty()) {
            Text(text = "Fecha de Fin: $fechaFin", color = MaterialTheme.colorScheme.primary)
        }

        // Botones de Guardar y Cancelar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && edad.isNotEmpty()) {
                        // Actualizar los datos del cliente
                        viewModel.actualizarCliente(
                            ClienteEntity(
                                id = cliente.id,
                                nombre = nombre,
                                apellido = apellido,
                                telefono = telefono.toInt(),
                                edad = edad.toInt(),
                                sexo = sexo,
                                tamaño = tamaño.toFloat(),
                                peso = peso.toFloat(),
                                membresiaId = selectedMembresia?.id,
                                fechaInicioMembresia = fechaInicio,
                                fechaFinMembresia = fechaFin
                            )
                        )
                        onSave() // Llamar al callback para volver
                    }
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text("Guardar")
            }

            OutlinedButton(
                onClick = { onCancel() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Cancelar")
            }
        }
    }
}

