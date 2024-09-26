package com.example.personaltrainner.ui

import MembresiaRegistroActivity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.data.MembresiaEntity


@Composable
fun MembresiaListScreen(viewModel: MembresiaViewModel, navigateToRegistro: () -> Unit) {
    val membresias by viewModel.todasLasMembresias.collectAsState(initial = emptyList())
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botón para agregar una nueva membresía
        Button(
            onClick = {
                navigateToRegistro()
            },
            modifier = Modifier.align(Alignment.End).padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Registrar Membresía")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar Membresía")
        }

        // Título de la pantalla
        Text(
            text = "Lista de Membresías",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de membresías
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(membresias) { membresia ->
                MembresiaCard(membresia = membresia, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MembresiaCard(membresia: MembresiaEntity, viewModel: MembresiaViewModel) {
    val context = LocalContext.current // Obtén el contexto de la actividad actual

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = membresia.nombre,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = 18.sp
                )
            )
            Text(text = "Tipo: ${membresia.tipo}")
            Text(text = "Descripción: ${membresia.descripcion}")
            Text(text = "Precio: $${membresia.precio}")

            Spacer(modifier = Modifier.height(8.dp))

            // Botones de Editar y Eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val intent = Intent(context, MembresiaRegistroActivity::class.java).apply {
                            putExtra("membresiaId", membresia.id) // Pasamos el ID de la membresía para editar
                        }
                        context.startActivity(intent) // Llamamos al método startActivity() con el contexto correcto
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }

                Button(
                    onClick = {
                        viewModel.eliminarMembresia(membresia) // Lógica para eliminar la membresía
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}
