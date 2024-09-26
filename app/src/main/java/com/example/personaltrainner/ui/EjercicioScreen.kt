package com.example.personaltrainner.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // Para obtener el contexto dentro de un Composable
import androidx.compose.ui.unit.dp
import com.example.personaltrainner.EjercicioEditarActivity
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.data.EjercicioEntity
import kotlinx.coroutines.launch

@Composable
fun EjercicioScreen(viewModel: EjercicioViewModel, navigateToRegistro: () -> Unit) {
    val ejercicios by viewModel.obtenerTodosLosEjercicios().collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current // Obtener el contexto dentro de un Composable

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botón para agregar nuevos ejercicios
        Button(
            onClick = { navigateToRegistro() },
            modifier = Modifier.align(Alignment.End).padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar Ejercicio")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Agregar Ejercicio")
        }

        // Título de la pantalla
        Text(
            text = "Lista de Ejercicios",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de ejercicios
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(ejercicios) { ejercicio ->
                EjercicioCard(
                    ejercicio = ejercicio,
                    onEdit = { ejercicioId ->
                        // Navegar a la pantalla de edición usando el contexto y un Intent
                        val intent = Intent(context, EjercicioEditarActivity::class.java).apply {
                            putExtra("ejercicioId", ejercicioId)
                        }
                        context.startActivity(intent) // Iniciar la actividad con el Intent
                    },
                    onDelete = { ejercicioId ->
                        coroutineScope.launch {
                            viewModel.eliminar(ejercicio)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EjercicioCard(
    ejercicio: EjercicioEntity,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = ejercicio.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descripción: ${ejercicio.descripcion}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Afecta a: ${ejercicio.tipo}")

            Spacer(modifier = Modifier.height(16.dp))

            // Botones para editar y eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onEdit(ejercicio.id) }, // Llamamos a la función de editar
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }

                Button(
                    onClick = { onDelete(ejercicio.id) }, // Llamamos a la función de eliminar
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}
