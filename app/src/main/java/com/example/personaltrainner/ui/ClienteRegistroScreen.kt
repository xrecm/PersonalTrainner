package com.example.personaltrainner.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
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

@Composable
fun ClienteRegistroScreen(viewModel: ClienteViewModel) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Seleccionar sexo") }
    var expanded by remember { mutableStateOf(false) }
    var tamaño by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }

    val opcionesSexo = listOf("Masculino", "Femenino")
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        // Campo de texto para el nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el apellido
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la edad
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown para seleccionar sexo
        Box {
            OutlinedTextField(
                value = sexo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sexo", color = Color.Black) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                ),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesSexo.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            sexo = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        // Campo de texto para el tamaño
        OutlinedTextField(
            value = tamaño,
            onValueChange = { tamaño = it },
            label = { Text("Tamaño (cm)", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el peso
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)", color = Color.Black) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar el cliente
        Button(
            onClick = {
                if (nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && edad.isNotEmpty() &&
                    sexo.isNotEmpty() && tamaño.isNotEmpty() && peso.isNotEmpty()) {
                    viewModel.insertarCliente(
                        ClienteEntity(
                            nombre = nombre,
                            apellido = apellido,
                            telefono = telefono.toInt(),
                            edad = edad.toInt(),
                            sexo = sexo,
                            tamaño = tamaño.toFloat(),
                            peso = peso.toFloat()
                        )
                    )
                    // Limpiar los campos
                    nombre = ""
                    apellido = ""
                    telefono = ""
                    edad = ""
                    sexo = "Seleccionar sexo"
                    tamaño = ""
                    peso = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
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
