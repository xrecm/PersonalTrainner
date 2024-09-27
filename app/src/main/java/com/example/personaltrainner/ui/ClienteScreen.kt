package com.example.personaltrainner.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.VerRutinaActivity
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.data.ClienteEntity
import com.example.personaltrainner.data.MembresiaEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ClienteListScreen(
    viewModel: ClienteViewModel,
    membresiaViewModel: MembresiaViewModel,
    navigateToRegistro: () -> Unit,
    navigateToRutina: (Int) -> Unit,
    navigateToEditar: (Int) -> Unit
) {
    val clientes by viewModel.obtenerTodosLosClientes().collectAsState(initial = emptyList())
    val membresias by membresiaViewModel.obtenerTodasLasMembresias().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón para registrar nuevos clientes
        Button(
            onClick = { navigateToRegistro() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Registrar Cliente")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar Cliente")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título de la pantalla
        Text(
            text = "Lista de Clientes",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // Lista de clientes
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(clientes) { cliente ->
                val membresia = membresias.find { it.id == cliente.membresiaId }
                ClienteCard(
                    cliente = cliente,
                    membresia = membresia,
                    onVerRutinaClick = { navigateToRutina(it.id) },
                    onEditarClick = { navigateToEditar(it.id) },
                    onEliminarClick = { viewModel.eliminarCliente(cliente) }
                )
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ClienteCard(
    cliente: ClienteEntity,
    membresia: MembresiaEntity?,
    onVerRutinaClick: (ClienteEntity) -> Unit,
    onEditarClick: (ClienteEntity) -> Unit,
    onEliminarClick: (ClienteEntity) -> Unit
) {
    val isActive = checkIfActive(cliente.fechaInicioMembresia, cliente.fechaFinMembresia)
    val statusColor = if (isActive) Color(0xFF4CAF50) else Color(0xFFF44336) // Verde para activo, rojo para inactivo
    val statusText = if (isActive) "Activo" else "Inactivo"
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
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
            // Encabezado con nombre y estatus
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${cliente.nombre} ${cliente.apellido}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "Teléfono: ${cliente.telefono}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }

                // Estado de actividad del cliente
                Box(
                    modifier = Modifier
                        .background(statusColor, shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

            // Detalles de la membresía
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Membresía: ${membresia?.nombre ?: "Sin membresía"}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "Estado: $statusText",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isActive) Color.Green else Color.Red
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Fechas de membresía
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Fecha de Inicio",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = cliente.fechaInicioMembresia ?: "N/A",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }

                Column {
                    Text(
                        text = "Fecha de Fin",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = cliente.fechaFinMembresia ?: "N/A",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Datos adicionales del cliente (edad, tamaño, peso)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Edad: ${cliente.edad} años",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Tamaño: ${cliente.tamaño} cm",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = "Peso: ${cliente.peso} kg",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val intent = Intent(context, VerRutinaActivity::class.java)
                        intent.putExtra("clienteId", cliente.id)
                        intent.putExtra("clienteNombre", cliente.nombre)
                        intent.putExtra("clienteApellido", cliente.apellido)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Text("Rutina")
                }

                Button(
                    onClick = { onEditarClick(cliente) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = { onEliminarClick(cliente) },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

// Función para verificar si la membresía está activa
fun checkIfActive(fechaInicio: String?, fechaFin: String?): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaActual = Date()

    return try {
        // Si alguna de las fechas es null, se considera que no está activo
        if (fechaInicio.isNullOrEmpty() || fechaFin.isNullOrEmpty()) return false

        val inicio = dateFormat.parse(fechaInicio)
        val fin = dateFormat.parse(fechaFin)
        fechaActual in inicio..fin
    } catch (e: Exception) {
        false
    }
}
