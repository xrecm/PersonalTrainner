package com.example.personaltrainner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalTrainnerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Obtenemos el contexto desde LocalContext
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título principal
            Text(
                text = "Bienvenido Entrenador",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botones de navegación
            NavigationButton(
                text = "Ir a Clientes",
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    val intent = Intent(context, ClienteListActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NavigationButton(
                text = "Ir a Ejercicios",
                backgroundColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    val intent = Intent(context, EjercicioActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NavigationButton(
                text = "Crear Rutina",
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                onClick = {
                    val intent = Intent(context, RutinaActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NavigationButton(
                text = "Ir a Membresías",
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                onClick = {
                    val intent = Intent(context, MembresiaListActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun NavigationButton(text: String, backgroundColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
        )
    }
}
