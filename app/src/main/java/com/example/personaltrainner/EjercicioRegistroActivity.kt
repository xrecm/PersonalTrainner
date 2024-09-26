package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.EjercicioViewModel
import com.example.personaltrainner.business.EjercicioViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.EjercicioRepository
import com.example.personaltrainner.ui.EjercicioRegistroScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class EjercicioRegistroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la base de datos y el repositorio
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = EjercicioRepository(database.ejercicioDao())

        // Crear el ViewModel usando el EjercicioViewModelFactory
        val ejercicioViewModel = ViewModelProvider(
            this,
            EjercicioViewModelFactory(repository)
        ).get(EjercicioViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                EjercicioRegistroScreen(viewModel = ejercicioViewModel)
            }
        }
    }
}
