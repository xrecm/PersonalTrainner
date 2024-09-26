package com.example.personaltrainner.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.data.MembresiaEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembresiaRegistroScreen(
    viewModel: MembresiaViewModel,
    membresiaId: Int? = null,  // Parámetro opcional para la edición
    onSave: () -> Unit,        // Callback para ejecutar cuando se guarde
    onCancel: () -> Unit       // Callback para volver
) {
    // Estado inicial para los campos
    var tipo by remember { mutableStateOf("Semanal") }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var isEditMode by remember { mutableStateOf(false) }
    var expandedTipo by remember { mutableStateOf(false) }  // Estado para el dropdown

    val tiposMembresia = listOf("Semanal", "Mensual", "3 Meses", "6 meses", "Anual")

    // Cargar los datos de la membresía si estamos en modo de edición
    LaunchedEffect(membresiaId) {
        if (membresiaId != null) {
            val membresia = viewModel.obtenerMembresiaPorId(membresiaId)
            if (membresia != null) {
                tipo = membresia.tipo
                nombre = membresia.nombre
                descripcion = membresia.descripcion
                precio = membresia.precio.toString()
                isEditMode = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Barra de título con botón de retroceso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onCancel() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.Black)
            }
            Text(
                text = if (isEditMode) "Editar Membresía" else "Registrar Membresía",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para seleccionar el tipo de membresía
        ExposedDropdownMenuBox(
            expanded = expandedTipo,
            onExpandedChange = { expandedTipo = !expandedTipo }
        ) {
            OutlinedTextField(
                value = tipo,
                onValueChange = {},
                label = { Text("Tipo de Membresía", color = Color.Black) },
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
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo)
                },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandedTipo,
                onDismissRequest = { expandedTipo = false }
            ) {
                tiposMembresia.forEach { tipoMembresia ->
                    DropdownMenuItem(
                        text = { Text(tipoMembresia) },
                        onClick = {
                            tipo = tipoMembresia
                            expandedTipo = false
                        }
                    )
                }
            }
        }

        // Campo de texto para el nombre de la membresía
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la Membresía", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        // Campo de texto para la descripción
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        // Campo de texto para el precio
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        // Botón para guardar la membresía (nueva o editada)
        Button(
            onClick = {
                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                    val membresia = MembresiaEntity(
                        id = membresiaId ?: 0,  // Si es nuevo, el id será 0
                        tipo = tipo,
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio.toDouble()
                    )

                    if (isEditMode) {
                        viewModel.actualizarMembresia(membresia)
                    } else {
                        viewModel.insertarMembresia(membresia)
                    }

                    onSave() // Volver a la pantalla anterior
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditMode) "Actualizar Membresía" else "Guardar Membresía")
        }

        // Botón para cancelar
        Button(
            onClick = { onCancel() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
