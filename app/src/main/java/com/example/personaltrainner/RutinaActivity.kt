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
            val clientes = database.clienteDao().obtenerTodosLosClientes() // Flow<List<ClienteEntity>>
            val ejercicios = database.ejercicioDao().obtenerTodosLosEjercicios() // Flow<List<EjercicioEntity>>

            clientes.collect { clientesList ->
                ejercicios.collect { ejerciciosList ->
                    setContent {
                        PersonalTrainnerTheme {
                            RutinaScreen(
                                viewModel = rutinaViewModel,
                                clientes = clientesList,
                                ejercicios = ejerciciosList
                            )
                        }
                    }
                }
            }
        }
    }
}
