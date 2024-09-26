package com.example.personaltrainner.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.data.ClienteEntity
import com.example.personaltrainner.data.MembresiaEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteRegistroScreen(viewModel: ClienteViewModel, membresias: List<MembresiaEntity>) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Seleccionar sexo") }
    var expandedSexo by remember { mutableStateOf(false) }
    var tamaño by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }

    var selectedMembresia by remember { mutableStateOf<MembresiaEntity?>(null) }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var expandedMembresia by remember { mutableStateOf(false) }

    val opcionesSexo = listOf("Masculino", "Femenino")
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current

    // Configuración del DatePicker para seleccionar la fecha de inicio
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaInicio = "$dayOfMonth/${month + 1}/$year"
            calcularFechaFin(selectedMembresia, fechaInicio)?.let { fechaFin = it }
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),  // Habilitar scroll vertical
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
        Text(
            text = "Registro de Cliente",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Log.d("ClienteRegistroScreen", "Membresías recibidas: $membresias")

        // Dropdown para seleccionar una membresía
        ExposedDropdownMenuBox(
            expanded = expandedMembresia,
            onExpandedChange = { expandedMembresia = !expandedMembresia }
        ) {
            OutlinedTextField(
                value = selectedMembresia?.nombre ?: "Seleccionar Membresía", // Mostramos el nombre de la membresía seleccionada o un texto por defecto
                onValueChange = {},
                label = { Text("Seleccionar Membresía", color = Color.Black) }, // Etiqueta
                readOnly = true, // Campo de solo lectura
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black), // Texto de color negro
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
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMembresia) // Icono de flecha
                },
                modifier = Modifier.fillMaxWidth().menuAnchor() // Expandir al ancho completo
            )

            ExposedDropdownMenu(
                expanded = expandedMembresia, // Control de expansión del menú
                onDismissRequest = { expandedMembresia = false } // Cerrar el menú si se hace clic afuera
            ) {
                membresias.forEach { membresia -> // Iteramos sobre la lista de membresías
                    DropdownMenuItem(
                        text = { Text(membresia.nombre) }, // Mostramos el nombre de la membresía en cada ítem
                        onClick = {
                            selectedMembresia = membresia // Asignamos la membresía seleccionada
                            expandedMembresia = false // Cerramos el menú
                        }
                    )
                }
            }
        }

        // Campo de texto para el nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el apellido
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la edad
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown para seleccionar sexo
        Box {
            OutlinedTextField(
                value = sexo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sexo") },
                trailingIcon = {
                    IconButton(onClick = { expandedSexo = !expandedSexo }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
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

        // Campo de texto para el tamaño
        OutlinedTextField(
            value = tamaño,
            onValueChange = { tamaño = it },
            label = { Text("Tamaño (cm)") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el peso
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para seleccionar la fecha de inicio
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (fechaInicio.isEmpty()) "Seleccionar Fecha de Inicio" else "Fecha de Inicio: $fechaInicio")
        }

        // Mostrar la fecha de fin calculada
        if (fechaFin.isNotEmpty()) {
            Text(text = "Fecha de Fin: $fechaFin", color = Color.Black)
        }

        // Botón para guardar el cliente con la membresía y fechas
        Button(
            onClick = {
                if (nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && edad.isNotEmpty() &&
                    sexo.isNotEmpty() && tamaño.isNotEmpty() && peso.isNotEmpty() && selectedMembresia != null && fechaInicio.isNotEmpty()) {

                    viewModel.insertarCliente(
                        ClienteEntity(
                            nombre = nombre,
                            apellido = apellido,
                            telefono = telefono.toInt(),
                            edad = edad.toInt(),
                            sexo = sexo,
                            tamaño = tamaño.toFloat(),
                            peso = peso.toFloat(),
                            membresiaId = selectedMembresia?.id ?: 0,
                            fechaInicioMembresia = fechaInicio,
                            fechaFinMembresia = fechaFin
                        )
                    )

                    nombre = ""
                    apellido = ""
                    telefono = ""
                    edad = ""
                    sexo = "Seleccionar sexo"
                    tamaño = ""
                    peso = ""
                    selectedMembresia = null
                    fechaInicio = ""
                    fechaFin = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cliente")
        }

        // Botón para regresar a la pantalla anterior
        OutlinedButton(
            onClick = { activity?.finish() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}

// Función para calcular la fecha de fin de la membresía
fun calcularFechaFin(membresia: MembresiaEntity?, fechaInicio: String): String? {
    if (membresia == null || fechaInicio.isEmpty()) return null

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaInicioDate = formatoFecha.parse(fechaInicio) ?: return null

    val calendario = Calendar.getInstance().apply { time = fechaInicioDate }
    when (membresia.tipo) {
        "Semanal" -> calendario.add(Calendar.DAY_OF_YEAR, 7)
        "Mensual" -> calendario.add(Calendar.DAY_OF_YEAR, 30)
        "3 Meses" -> calendario.add(Calendar.DAY_OF_YEAR, 90)
        "6 Meses" -> calendario.add(Calendar.DAY_OF_YEAR, 180)
        "Anual" -> calendario.add(Calendar.DAY_OF_YEAR, 365)
    }
    return formatoFecha.format(calendario.time)
}