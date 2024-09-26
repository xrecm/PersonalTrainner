package com.example.personaltrainner.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.data.EjercicioEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjercicioEditarScreen(viewModel: EjercicioViewModel, ejercicioId: Int, onSave: () -> Unit, onCancel: () -> Unit) {
    // Recolectamos el ejercicio desde el Flow usando collectAsState
    val ejercicio by viewModel.obtenerEjercicioPorId(ejercicioId).collectAsState(initial = null)

    // Usar estados directamente dependientes del ejercicio
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val partesCuerpo = listOf("Brazos", "Pecho", "Espalda", "Hombros", "Abdomen", "Piernas", "Gluteos")
    val partesSeleccionadas = remember { mutableStateMapOf<String, Boolean>() }

    // Actualizar los campos cuando los datos del ejercicio estén disponibles
    LaunchedEffect(ejercicio) {
        if (ejercicio != null) {
            nombre = ejercicio!!.nombre
            descripcion = ejercicio!!.descripcion
            partesCuerpo.forEach { parte ->
                partesSeleccionadas[parte] = ejercicio!!.tipo?.contains(parte) ?: false
            }
        }
    }

    // Obtener el contexto para finalizar la actividad
    val activity = LocalContext.current as? Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Editar Ejercicio",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Campo de texto para el nombre del ejercicio
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Ejercicio", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
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
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Checkbox para seleccionar las partes del cuerpo afectadas
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

        // Botón para guardar los cambios
        Button(
            onClick = {
                if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                    val partesSeleccionadasList = partesSeleccionadas.filter { it.value }.keys.toList()
                    val partesAfectadas = partesSeleccionadasList.joinToString(", ")

                    viewModel.actualizarEjercicio(
                        EjercicioEntity(
                            id = ejercicioId, // Mantiene el ID del ejercicio a actualizar
                            nombre = nombre,
                            descripcion = descripcion,
                            tipo = partesAfectadas
                        )
                    )

                    // Finalizar la actividad y regresar a la pantalla anterior
                    onSave()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios")
        }

        // Botón para cancelar la edición y volver a la pantalla anterior
        Button(
            onClick = { onCancel() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
