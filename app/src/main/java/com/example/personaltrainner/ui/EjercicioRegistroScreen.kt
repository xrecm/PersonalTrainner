package com.example.personaltrainner.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.data.EjercicioEntity
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjercicioRegistroScreen(viewModel: EjercicioViewModel) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Estado para los checkboxes
    val partesCuerpo = listOf("Brazos", "Pecho", "Espalda", "Hombros", "Abdomen", "Piernas", "Gluteos")
    val partesSeleccionadas = remember { mutableStateMapOf<String, Boolean>() }

    partesCuerpo.forEach {
        partesSeleccionadas[it] = partesSeleccionadas[it] ?: false
    }

    // Obtener el contexto de la actividad actual para cerrar la pantalla
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Registrar Ejercicio",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 24.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Campo de texto para el nombre del ejercicio
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Ejercicio", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black), // Forzar color negro para el texto
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la descripción del ejercicio
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción del Ejercicio", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black), // Forzar color negro para el texto
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Checkbox para seleccionar las partes del cuerpo
        Text(text = "Partes del cuerpo afectadas:")
        Column {
            partesCuerpo.forEach { parte ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = parte)
                    Checkbox(
                        checked = partesSeleccionadas[parte] ?: false,
                        onCheckedChange = { isChecked ->
                            partesSeleccionadas[parte] = isChecked
                        }
                    )
                }
            }
        }

        // Botón para guardar el ejercicio en la base de datos
        Button(
            onClick = {
                if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                    val partesSeleccionadasList = partesSeleccionadas.filter { it.value }.keys.toList()
                    val partesAfectadas = partesSeleccionadasList.joinToString(", ")

                    viewModel.insertarEjercicio(
                        EjercicioEntity(
                            nombre = nombre,
                            descripcion = descripcion,
                            tipo = partesAfectadas
                        )
                    )

                    // Limpiar los campos después de guardar
                    nombre = ""
                    descripcion = ""
                    partesSeleccionadas.keys.forEach { partesSeleccionadas[it] = false }

                    // Finalizar la actividad y regresar a la pantalla anterior
                    activity?.finish()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Ejercicio")
        }

        // Botón para regresar a la pantalla anterior
        Button(
            onClick = { activity?.finish() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
