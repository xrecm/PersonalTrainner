package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.personaltrainner.business.RutinaViewModel
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.RutinaRepository
import com.example.personaltrainner.ui.RutinaScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

class RutinaActivity : ComponentActivity() {

    private lateinit var rutinaViewModel: RutinaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos y el repositorio
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = RutinaRepository(database.rutinaDao())

        // Inicializar el ViewModel
        rutinaViewModel = RutinaViewModel(repository)

        // Obtener clientes y ejercicios
        lifecycleScope.launch {
            // Recoger los datos de clientes y ejercicios
            val clientesList = database.clienteDao().obtenerTodosLosClientes().first()
            val ejerciciosList = database.ejercicioDao().obtenerTodosLosEjercicios().first()

            // Establecer la UI
            setContent {
                PersonalTrainnerTheme {
                    RutinaScreen(
                        viewModel = rutinaViewModel,
                        clientes = clientesList,
                        ejercicios = ejerciciosList,
                        onCancel = { finish() } // Callback para cancelar y volver
                    )
                }
            }
        }
    }
}
