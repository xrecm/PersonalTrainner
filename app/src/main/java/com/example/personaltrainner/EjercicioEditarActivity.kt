package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.ui.EjercicioEditarScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.business.EjercicioViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.EjercicioRepository

class EjercicioEditarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ejercicioId = intent.getIntExtra("ejercicioId", -1) // Obtener el ID del ejercicio

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = EjercicioRepository(database.ejercicioDao())
        val viewModel = ViewModelProvider(this, EjercicioViewModelFactory(repository))
            .get(EjercicioViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                EjercicioEditarScreen(
                    viewModel = viewModel,
                    ejercicioId = ejercicioId,
                    onSave = { finish() }, // Volver después de guardar
                    onCancel = { finish() } // Volver sin guardar
                )
            }
        }
    }
}
