package com.example.personaltrainner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.data.EjercicioEntity

@Composable
fun EjercicioScreen(viewModel: EjercicioViewModel, navigateToRegistro: () -> Unit) {
    val ejercicios by viewModel.obtenerTodosLosEjercicios().collectAsState(initial = emptyList())

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
                EjercicioCard(ejercicio)
            }
        }
    }
}

@Composable
fun EjercicioCard(ejercicio: EjercicioEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = ejercicio.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descripción: ${ejercicio.descripcion}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Afecta a: ${ejercicio.tipo}")
        }
    }
}
