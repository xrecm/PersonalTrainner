package com.example.personaltrainner.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.ClienteRegistroActivity
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.data.ClienteEntity
import kotlinx.coroutines.launch

@Composable
fun ClienteListScreen(viewModel: ClienteViewModel, navigateToRegistro: () -> Unit) {
    val clientes by viewModel.obtenerTodosLosClientes().collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botón para registrar nuevos clientes
        Button(
            onClick = {
                coroutineScope.launch {
                    navigateToRegistro()  // Navegación hacia la vista de registro
                }
            },
            modifier = Modifier.align(Alignment.End).padding(bottom = 16.dp),
        ) {
            Icon(Icons.Default.Add, contentDescription = "Registrar Cliente")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar Cliente")
        }

        // Título de la pantalla
        Text(
            text = "Lista de Clientes",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de clientes
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(clientes) { cliente ->
                ClienteCard(cliente)
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun ClienteCard(cliente: ClienteEntity) {
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
                .padding(16.dp)
        ) {
            Text(
                text = "${cliente.nombre} ${cliente.apellido}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Sexo: ${cliente.sexo}")
                Text(text = "Edad: ${cliente.edad} años")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tamaño: ${cliente.tamaño} cm")
            Text(text = "Peso: ${cliente.peso} kg")
            Text(text = "Teléfono: ${cliente.telefono}")

            Spacer(modifier = Modifier.height(8.dp))

            // Botones de "Ver Rutina", "Editar", "Eliminar"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // Lógica para ver rutina del cliente
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Text("Ver Rutina")
                }

                Button(
                    onClick = {
                        // Lógica para editar el cliente
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = {
                        // Lógica para eliminar el cliente
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}
