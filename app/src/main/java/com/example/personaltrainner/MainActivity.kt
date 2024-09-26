package com.example.personaltrainner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalTrainnerTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Button(onClick = {
                        // Redirige a la lista de clientes
                        val intent = Intent(this@MainActivity, ClienteListActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text(text = "Ir a Clientes")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para ir a la pantalla de texto
                    Button(onClick = {
                        startActivity(Intent(this@MainActivity, EjercicioActivity::class.java))
                    }) {
                        Text(text = "Ir a Ejercicios")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para ir a la pantalla de texto
                    Button(onClick = {
                        startActivity(Intent(this@MainActivity, PlanSemanalActivity::class.java))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Rutina de la semana")
                    }
                }
            }
        }
    }
}